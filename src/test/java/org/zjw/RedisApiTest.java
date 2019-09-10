package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.zjw.web.util.LockModel;
import org.zjw.web.util.LockUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouminsen on 2018/7/31.
 */
public class RedisApiTest extends BaseTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 测试list
     */
    @Test
    public void list() {
        String list_key = "list";
//        https://github.com/Snailclimb/JavaGuide

        stringRedisTemplate.delete(list_key);
        stringRedisTemplate.opsForList().leftPushAll(list_key, "1", "2", "3", "4", "5", "6", "7", "8", "9");
        System.out.println(stringRedisTemplate.opsForList().range(list_key, 0, -1));
        stringRedisTemplate.opsForList().leftPush(list_key, "11");

        System.out.println(stringRedisTemplate.opsForList().range(list_key, 0, -1));

        stringRedisTemplate.opsForList().leftPop("cc", 5, TimeUnit.SECONDS);

    }

    /**
     * 测试list
     */
    @Test
    public void set() {
        String set_key = "set";
//        https://github.com/Snailclimb/JavaGuide

     /*   stringRedisTemplate.delete(set_key);
        System.out.println("我add进去了吗" + stringRedisTemplate.opsForSet().add(set_key, "1"));
        stringRedisTemplate.expire(set_key, 1, TimeUnit.SECONDS);
        System.out.println("我add进去了吗" + stringRedisTemplate.opsForSet().add(set_key, "1"));
*/

        System.out.println("我add进去了吗" + setLock("1", 50L));
        System.out.println("我add进去了吗" + setLock("2", 50L));
        unlock("1");

    }

    /**
     * 测试zset
     */
    @Test
    public void zset() {
        String set_key = "zset";
        stringRedisTemplate.opsForZSet().add("1", "周家伟2", 1.2);
        stringRedisTemplate.opsForZSet().add("1", "周家伟1", 1.1);
        stringRedisTemplate.opsForZSet().add("1", "周家伟3", 1.5);
        stringRedisTemplate.opsForZSet().add("1", "周家伟4", 1.3);
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.boundZSetOps("1").rangeWithScores(1, 5);
        for (ZSetOperations.TypedTuple<String> item : typedTuples) {
            System.out.println(item.getValue() + ":" + item.getScore());
        }

    }

    /**
     * 测试list
     */
    @Test
    public void lock() {
        LockUtil lockUtil = new LockUtil(stringRedisTemplate);
        HashSet<String> strings = new HashSet<>(Arrays.asList("1", "2", "3", "4"));
        LockModel lockModel = lockUtil.setRangeLock(strings, 30L);
        stringRedisTemplate.boundHashOps("account").expire(30L, TimeUnit.SECONDS);
        strings.clear();
        strings.addAll(Arrays.asList("0", "1"));
        LockModel lockModel2 = lockUtil.setRangeLock(strings, 30L);
        lockUtil.unlock(lockModel);
        lockUtil.unlock(lockModel2);

    }

    /**
     * 测试list
     */
    @Test
    public void hash() {
        String hash_key = "hash";
//        https://github.com/Snailclimb/JavaGuide

        stringRedisTemplate.delete(hash_key);
        stringRedisTemplate.opsForHash().put(hash_key, "1", "zjw");
        System.out.println(stringRedisTemplate.opsForHash().get(hash_key, "1"));

        stringRedisTemplate.opsForHash().put(hash_key, "1", "wjz");
        System.out.println(stringRedisTemplate.opsForHash().get(hash_key, "1"));


    }

    private Boolean setLock(String value, Long expire) {
        String lockKey = "account";
        SessionCallback<Boolean> sessionCallback = new SessionCallback<Boolean>() {
            List<Object> exec = null;

            @Override
            @SuppressWarnings("unchecked")
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                stringRedisTemplate.opsForSet().add(lockKey, value);
                stringRedisTemplate.expire(lockKey, expire, TimeUnit.SECONDS);
                exec = operations.exec();
                if (exec.size() > 0) {
                    long i = (long) exec.get(0);
                    if (i == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        };
        return stringRedisTemplate.execute(sessionCallback);
    }

    private void unlock(String key) {
        String lockKey = "account";
        stringRedisTemplate.boundSetOps(lockKey).remove(key);
    }

    @Test
    public void increment() {
        String key = "fdf6cfbd-d19f-42ad-b0a7-b11c489d2603";
        stringRedisTemplate.opsForValue().set(key, "0", 1000);
        for (int i = 0; i < 50; i++) {
            System.out.println(stringRedisTemplate.boundValueOps(key).increment(1));
        }

    }
}
