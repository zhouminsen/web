package org.zjw;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.apache.lucene.spatial.prefix.NumberRangePrefixTreeStrategy;
import org.apache.xmlbeans.impl.xb.xsdschema.Facet;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zjw.web.entity.User;
import org.zjw.web.util.RandomName;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Elasticsearch的基本测试
 *
 * @author sunt
 * @version V1.0
 * @ClassName: ElasticsearchTest
 * @date 2017年11月22日
 */
public class ElasticsearchTest {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchTest.class);

    public final static String HOST = "192.168.1.113";

    public final static int PORT = 9300;//http请求的端口是9200，客户端是9300
    private TransportClient client = null;

    /**
     * 获取客户端连接信息
     *
     * @return void
     * @throws UnknownHostException
     * @Title: getConnect
     * @author sunt
     * @date 2017年11月23日
     */
    @SuppressWarnings({"resource", "unchecked"})
    @Before
    public void getConnect() throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(
                new TransportAddress(InetAddress.getByName(HOST), PORT));
        logger.info("连接信息:" + client.toString());
    }

    /**
     * 关闭连接
     *
     * @return void
     * @Title: closeConnect
     * @author sunt
     * @date 2017年11月23日
     */
    @After
    public void closeConnect() {
        if (null != client) {
            logger.info("执行关闭连接操作...");
            client.close();
        }
    }


    /**
     * 创建索引库
     *
     * @return void
     * 需求:创建一个索引库为：msg消息队列,类型为：tweet,id为1
     * 索引库的名称必须为小写
     * @throws IOException
     * @Title: addIndex1
     * @author sunt
     * @date 2017年11月23日
     */
    @Test
    public void addIndex1() throws IOException {
        IndexResponse response = client.prepareIndex("msg", "tweet", "1").
                setSource(XContentFactory.jsonBuilder()
                        .startObject().field("userName", "张三")
                        .field("sendDate", new Date())
                        .field("msg", "你好李四")
                        .endObject()).get();

        logger.info("索引名称:" + response.getIndex() + "\n类型:" + response.getType()
                + "\n文档ID:" + response.getId() + "\n当前实例状态:" + response.status());
    }

    /**
     * 添加索引:传入json字符串
     *
     * @return void
     * @Title: addIndex2
     * @author sunt
     * @date 2017年11月23日
     */
    @Test
    public void addIndex2() {
        String jsonStr = "{" +
                "\"userName\":\"张三\"," +
                "\"sendDate\":\"2017-11-30\"," +
                "\"msg\":\"你好李四\"" +
                "}";
        IndexResponse response = client.prepareIndex("weixin", "tweet").
                setSource(jsonStr, XContentType.JSON).get();
        logger.info("json索引名称:" + response.getIndex() + "\njson类型:" + response.getType()
                + "\njson文档ID:" + response.getId() + "\n当前实例json状态:" + response.status());

    }

    /**
     * 创建索引-传入Map对象
     *
     * @return void
     * @Title: addIndex3
     * @author sunt
     * @date 2017年11月23日
     */
    @Test
    public void addIndex3() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "张三");
        map.put("sendDate", new Date());
        map.put("msg", "你好李四");

        IndexResponse response = client.prepareIndex("momo", "tweet").setSource(map).get();

        logger.info("map索引名称:" + response.getIndex() + "\n map类型:" + response.getType()
                + "\n map文档ID:" + response.getId() + "\n当前实例map状态:" + response.status());
    }

    /**
     * 传递json对象
     * 需要添加依赖:gson
     *
     * @return void
     * @Title: addIndex4
     * @author sunt
     * @date 2017年11月23日
     */
    @Test
    public void addIndex4() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "张三");
        jsonObject.put("sendDate", "2017-11-23");
        jsonObject.put("msg", "你好李四");

        IndexResponse response = client.prepareIndex("qq", "tweet").
                setSource(jsonObject, XContentType.JSON).get();

        logger.info("jsonObject索引名称:" + response.getIndex() + "\n jsonObject类型:" + response.getType()
                + "\n jsonObject文档ID:" + response.getId() + "\n当前实例jsonObject状态:" + response.status());
    }

    /**
     * 从索引库获取数据
     *
     * @return void
     * @Title: getData1
     * @author sunt
     * @date 2017年11月23日
     */
    @Test
    public void getData1() {
        GetResponse getResponse = client.prepareGet("msg", "tweet", "1").get();
        logger.info("索引库的数据:" + getResponse.getSourceAsString());
    }

    /**
     * 更新索引库数据
     *
     * @return void
     * @Title: updateData
     * @author sunt
     * @date 2017年11月23日
     */
    @Test
    public void updateData() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userName", "王五");
        jsonObject.put("sendDate", "2008-08-08");
        jsonObject.put("msg", "你好,张三，好久不见");

        UpdateResponse updateResponse = client.prepareUpdate("msg", "tweet", "1")
                .setDoc(jsonObject.toString(), XContentType.JSON).get();

        logger.info("updateResponse索引名称:" + updateResponse.getIndex() + "\n updateResponse类型:" + updateResponse.getType()
                + "\n updateResponse文档ID:" + updateResponse.getId() + "\n当前实例updateResponse状态:" + updateResponse.status());
    }

    /**
     * 根据索引名称，类别，文档ID 删除索引库的数据
     *
     * @return void
     * @Title: deleteData
     * @author sunt
     * @date 2017年11月23日
     */
    @Test
    public void deleteData() {
        DeleteResponse deleteResponse = client.prepareDelete("msg", "tweet", "1").get();

        logger.info("deleteResponse索引名称:" + deleteResponse.getIndex() + "\n deleteResponse类型:" + deleteResponse.getType()
                + "\n deleteResponse文档ID:" + deleteResponse.getId() + "\n当前实例deleteResponse状态:" + deleteResponse.status());
    }


    /**
     * 添加数据
     *
     * @param
     * @throws Exception
     */
    @Test
    public void prepareData() throws Exception {
        XContentBuilder builder1 = XContentFactory.jsonBuilder().startObject()
                .field("name", "jack")
                .field("age", 27)
                .field("position", "technique software")
                .field("country", "China")
                .field("join_date", "2017-01-01")
                .field("salary", "10000")
                .endObject();
        client.prepareIndex("company", "employee", "1").setSource(builder1).get();

        XContentBuilder builder2 = XContentFactory.jsonBuilder().startObject()
                .field("name", "marry")
                .field("age", 35)
                .field("position", "technique manager")
                .field("country", "china")
                .field("join_date", "2017-01-01")
                .field("salary", 12000)
                .endObject();
        client.prepareIndex("company", "employee", "2").setSource(builder2).get();

        XContentBuilder builder3 = XContentFactory.jsonBuilder().startObject()
                .field("name", "tom")
                .field("age", 32)
                .field("position", "senior technique software")
                .field("country", "china")
                .field("join_date", "2016-01-01")
                .field("salary", 11000)
                .endObject();
        client.prepareIndex("company", "employee", "3").setSource(builder3).get();

        XContentBuilder builder4 = XContentFactory.jsonBuilder().startObject()
                .field("name", "jen")
                .field("age", 25)
                .field("position", "junior finance")
                .field("country", "usa")
                .field("join_date", "2016-01-01")
                .field("salary", 7000)
                .endObject();
        client.prepareIndex("company", "employee", "4").setSource(builder4).get();

        XContentBuilder builder5 = XContentFactory.jsonBuilder().startObject()
                .field("name", "mike")
                .field("age", 37)
                .field("position", "finance manager")
                .field("country", "usa")
                .field("join_date", "2015-01-01")
                .field("salary", 15000)
                .endObject();
        client.prepareIndex("company", "employee", "5").setSource(builder5).get();

    }

    /**
     * 搜索
     *
     * @param
     */
    @Test
    public void executeSearch() {

        MatchQueryBuilder builder = QueryBuilders.matchQuery("position", "technique");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").from(30).to(40);
        SearchResponse response = client.prepareSearch("company").setTypes("employee").setQuery(builder)
                .setPostFilter(rangeQueryBuilder)
                .setFrom(0)
                .setSize(1) //设置搜索结果个数
                .get();
        SearchHit[] hits = response.getHits().getHits();
        System.out.println(1234);
        for (int i = 0; i < hits.length; i++) {
            System.out.println(hits[i].getSourceAsString());
        }
    }

    /**
     * boolean搜索
     *
     * @param
     */
    @Test
    public void executeSearch2() {
        BoolQueryBuilder query = QueryBuilders.boolQuery().
                must(QueryBuilders.matchQuery("position", "finance"))
//                .must(QueryBuilders.rangeQuery("age").lte(25))
                ;
        SearchResponse response = client.prepareSearch("company").setTypes("employee").setQuery(query)
                .setFrom(0).get();
        SearchHit[] hits = response.getHits().getHits();
        System.out.println(1234);
        for (int i = 0; i < hits.length; i++) {
            System.out.println(hits[i].getSourceAsString());
        }
    }


    /**
     * 批量插入
     */
    @Test
    public void batchInsert() {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        User user = new User();
        for (int i = 0; i < 100; i++) {
            user.setName(RandomName.getEnglishName());
            Random random = new Random();
            long num = Math.abs(random.nextInt(200));
            user.setWeight(num);
            user.setMarried(num % 2 == 0 ? true : false);
            user.setAge(Math.abs(random.nextInt(100)));

            IndexRequestBuilder ir = client.prepareIndex()
                    .setIndex("java_demo_index")
                    .setType("bulk_user")
                    .setId("" + i)
                    .setSource(JSON.toJSONString(user), XContentType.JSON);
            bulkRequest.add(ir);
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (!bulkResponse.hasFailures()) {
            System.out.println("全部插入成功");
        }
    }

    /**
     * 批量删除
     */
    @Test
    public void batchDel() {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 10; i < 100; i++) {
            DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete()
                    .setIndex("java_demo_index")
                    .setType("bulk_user")
                    .setId("" + i);
            bulkRequest.add(deleteRequestBuilder);
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (!bulkResponse.hasFailures()) {
            System.out.println("全部删除成功");
        }
    }

    //分组统计, 按married分组，统计出结婚和未婚的各多少
    @Test
    public void facet(){
    }

}