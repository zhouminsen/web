package org.zjw.web.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhoum on 2019-05-23.
 */

public class LockUtil {

    private StringRedisTemplate stringRedisTemplate;

    public LockUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 阻塞取锁
     *
     * @param keys
     */
    public LockModel blockingRangeLock(HashSet<String> keys, Long expire) {
        LockModel lockModel = new LockModel();
        for (String item : keys) {
            LockModel e = blockingLock(item, expire);
            lockModel.getLockModels().addAll(e.getLockModels());
        }
        lockModel.setFlag(Boolean.TRUE);
        return lockModel;
    }

    /**
     * 阻塞取锁
     *
     * @param key
     */
    public LockModel blockingLock(String key, Long expire) {
        LockModel e = setLock(key, expire);
        while (!e.getFlag()) {
            //自循取锁，直至拿到锁
            e = setLock(key, expire);
        }
        e.setFlag(Boolean.TRUE);
        return e;
    }

    public LockModel setRangeLock(HashSet<String> keys, Long expire) {
        LockModel lockModel = new LockModel();
        for (String item : keys) {
            LockModel e = setLock(item, expire);
            if (!e.getFlag()) {
                //加锁失败需要把之前加的锁释放
                unlock(lockModel);
                lockModel.getLockModels().clear();
                lockModel.setFlag(Boolean.FALSE);
                return lockModel;
            }
            lockModel.getLockModels().addAll(e.getLockModels());
        }
        lockModel.setFlag(Boolean.TRUE);
        return lockModel;
    }

    public LockModel setLock(String set, Long expire) {
        LockModel lockModel = new LockModel();
        Boolean aBoolean = tryingLock(set, expire);
        if (!aBoolean) {
            lockModel.setFlag(Boolean.FALSE);
        } else {
            lockModel.getLockModels().add(set);
            lockModel.setFlag(Boolean.TRUE);
        }
        return lockModel;
    }

    private Boolean tryingLock(String key, Long expire) {
        String lockKey = "account";
        SessionCallback<Boolean> sessionCallback = new SessionCallback<Boolean>() {
            List<Object> exec = null;

            @Override
            @SuppressWarnings("unchecked")
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                stringRedisTemplate.opsForSet().add(lockKey, key);
                stringRedisTemplate.expire(lockKey, expire, TimeUnit.SECONDS);
                exec = operations.exec();
                if (exec.size() > 0) {
                    long i = (long) exec.get(0);
                    if (i == 1) {
                        return Boolean.TRUE;
                    }
                }
                return Boolean.FALSE;
            }
        };
        return stringRedisTemplate.execute(sessionCallback);
    }

    public void unlock(LockModel lockModel) {
        delete(lockModel.getLockModels());
    }

    private void delete(HashSet<String> keys) {
        String lockKey = "account";
        Boolean aBoolean = stringRedisTemplate.hasKey(lockKey);
        if (aBoolean) {
            for (String item : keys) {
                stringRedisTemplate.boundSetOps(lockKey).remove(item);
            }
        }
    }
}
