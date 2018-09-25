package org.zjw;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
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
import java.util.*;


/**
 * 参考:https://blog.csdn.net/liuxiao723846/article/details/78709919
 * Elasticsearch的基本测试
 *
 * @author zjw
 * @version V1.0
 * @ClassName: ElasticsearchTest
 * @date 2018年8月13日
 */
public class ElasticsearchTest {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchTest.class);

    public final static String HOST = "127.0.0.1";

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
        IndexResponse response = client.prepareIndex("msg", "tweet", "3").
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
     * 多选条件
     */
    @Test
    public void getData2() {
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet().add("msg", "tweet", "1", "2", "3").get();
        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse getResponse = itemResponse.getResponse();
            logger.info("索引库的数据:" + getResponse.getSourceAsString());
        }
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

//        jsonObject.put("userName", "王五");
//        jsonObject.put("sendDate", "2008-08-08");
//        jsonObject.put("msg", "你好,张三，好久不见");
        jsonObject.put("age", 11);

        UpdateResponse updateResponse = client.prepareUpdate("java_demo_index", "bulk_user", "5")
                .setDoc(jsonObject.toString(), XContentType.JSON).get();

        logger.info("updateResponse索引名称:" + updateResponse.getIndex() + "\n updateResponse类型:" + updateResponse.getType()
                + "\n updateResponse文档ID:" + updateResponse.getId() + "\n当前实例updateResponse状态:" + updateResponse.status());
    }

    /**
     * 根据索引名称，类别，文档ID 删除索引库的数据
     *
     * @return void
     * @Title: deleteData
     * @author zehe5y
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
    public void matchQuery() {
        MatchQueryBuilder builder = QueryBuilders.matchQuery("position", "technique");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").from(30).to(40);
        SearchResponse response = client.prepareSearch("company").setTypes("employee").setQuery(builder)
                .setPostFilter(rangeQueryBuilder)
                .setFrom(0)
                .setSize(1) //设置搜索结果个数
                .get();
        System.out.println(builder.toString());
        SearchHit[] hits = response.getHits().getHits();
        System.out.println("查询出来的结果:");
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
    public void boolQuery() {
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
     * 搜索全部
     *
     * @param
     */
    @Test
    public void matchAllQuery() {
        QueryBuilder builder = QueryBuilders.matchAllQuery();
        SearchResponse response = client.prepareSearch("company").setTypes("employee").setQuery(builder)
                .setFrom(0)
                .get();
        SearchHit[] hits = response.getHits().getHits();
        System.out.println("查询出来的结果:");
        for (int i = 0; i < hits.length; i++) {
            System.out.println(hits[i].getSourceAsString());
        }
    }


    /**
     * 多个字段搜索
     *
     * @param
     */
    @Test
    public void multiMatchQuery() {
        //在age,weight字段中匹配68
        QueryBuilder qb = QueryBuilders.multiMatchQuery("68", "age", "weight").operator(Operator.OR);
        SearchResponse searchResponse = client.prepareSearch("java_demo_index").setTypes("bulk_user").setQuery(qb).get();
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit sh : hits2) {
            Map<String, Object> source = sh.getSourceAsMap();
            String id = sh.getId();
            //sh.getScore();
            System.out.println(totalHits + "," + source + "," + id);
        }

    }

    /**
     * 搜索
     *
     * @param
     */
    @Test
    public void queryStringQuery() {
        //所有field匹配
        QueryBuilder qb = QueryBuilders.queryStringQuery("Berry");
        SearchResponse searchResponse = client.prepareSearch("java_demo_index").setTypes("bulk_user").setQuery(qb).get();
        System.out.println(qb.toString());
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit sh : hits2) {
            Map<String, Object> source = sh.getSourceAsMap();
            String id = sh.getId();
            //sh.getScore();
            System.out.println(totalHits + "," + source + "," + id);
        }

    }

    /**
     * 短语查询
     */
    @Test
    public void termQuery() {
        QueryBuilder qb = QueryBuilders.termsQuery("name", "steve", "stacy");
        SearchResponse searchResponse = client.prepareSearch("java_demo_index").setTypes("bulk_user").setQuery(qb).get();
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit sh : hits2) {
            Map<String, Object> source = sh.getSourceAsMap();
            String id = sh.getId();
            //sh.getScore();
            System.out.println(totalHits + "," + source + "," + id);
        }
    }

    /**
     * 前缀查询(必须匹配短词的前面部分或整个)
     */
    @Test
    public void prefixQuery() {
        QueryBuilder qb = QueryBuilders.prefixQuery("name", "st");
        SearchResponse searchResponse = client.prepareSearch("java_demo_index").setTypes("bulk_user").setQuery(qb).get();
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit sh : hits2) {
            Map<String, Object> source = sh.getSourceAsMap();
            String id = sh.getId();
            //sh.getScore();
            System.out.println(totalHits + "," + source + "," + id);
        }
    }

    /**
     * 短词匹配(必须匹配短词或整词的全部)
     */
    @Test
    public void matchPhraseQuery() {
        QueryBuilder qb = QueryBuilders.matchPhraseQuery("name", "steve rose");
        SearchResponse searchResponse = client.prepareSearch("java_demo_index").setTypes("bulk_user").setQuery(qb).get();
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit sh : hits2) {
            Map<String, Object> source = sh.getSourceAsMap();
            String id = sh.getId();
            //sh.getScore();
            System.out.println(totalHits + "," + source + "," + id);
        }
    }

    /**
     * 短语前缀匹配
     */
    @Test
    public void matchPhrasePrefixQuery() {
        QueryBuilder qb = QueryBuilders.matchPhrasePrefixQuery("name", "steve").slop(3).maxExpansions(2);

        SearchResponse searchResponse = client.prepareSearch("java_demo_index").setTypes("bulk_user").setQuery(qb).get();
        SearchHits hits = searchResponse.getHits();
        //long totalHits = hits.getTotalHits();
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit sh : hits2) {
            Map<String, Object> source = sh.getSourceAsMap();
            String id = sh.getId();
            float score = sh.getScore();
            System.out.println("source:" + source + ",id:" + id + ",score:" + score);
        }
    }


    /**
     * 模糊查询（fuzzy）
     * Fuzziness.fromEdits(2) 错误匹配个数
     */
    @Test
    public void fuzzyQuery() {
        QueryBuilder qb = QueryBuilders.fuzzyQuery("name", "seeee").fuzziness(Fuzziness.fromEdits(2));
//                .prefixLength(2).maxExpansions(50);
        SearchResponse searchResponse = client.prepareSearch("java_demo_index").setTypes("bulk_user").setQuery(qb).get();
        SearchHits hits = searchResponse.getHits();
        //long totalHits = hits.getTotalHits();
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit sh : hits2) {
            Map<String, Object> source = sh.getSourceAsMap();
            String id = sh.getId();
            float score = sh.getScore();
            System.out.println("source:" + source + ",id:" + id + ",score:" + score);
        }
    }

    /**
     * 通配符查询
     */
    @Test
    public void wildCardQuery() {
        QueryBuilder qb = QueryBuilders.wildcardQuery("name", "st*");
        SearchResponse searchResponse = client.prepareSearch("java_demo_index").setTypes("bulk_user").setQuery(qb).get();
        SearchHits hits = searchResponse.getHits();
        //long totalHits = hits.getTotalHits();
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit sh : hits2) {
            Map<String, Object> source = sh.getSourceAsMap();
            String id = sh.getId();
            float score = sh.getScore();
            System.out.println("source:" + source + ",id:" + id + ",score:" + score);
        }
    }

    /**
     * 聚合分组查询
     * 根据married和age进行分组
     */
    @Test
    public void aggreQuery1() {
        SearchRequestBuilder srb = client.prepareSearch("java_demo_index").setTypes("bulk_user");
//        srb.setSearchType(SearchType.COUNT);

        TermsAggregationBuilder teamAgg = AggregationBuilders.terms("married_count").field("married");
        TermsAggregationBuilder positionAgg = AggregationBuilders.terms("age_count").field("age");
        teamAgg.subAggregation(positionAgg);

        srb.addAggregation(teamAgg);
        System.out.println(srb.toString());

        SearchResponse searchResponse = srb.execute().actionGet();
        Aggregations aggregations = searchResponse.getAggregations();//team agg
        Terms terms = aggregations.get("married_count");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bt : buckets) {
            logger.info(bt.getKeyAsString() + " :: " + bt.getDocCount());

            Aggregations aggregations2 = bt.getAggregations();//position agg
            Terms terms2 = aggregations2.get("age_count");
            List<? extends Terms.Bucket> buckets2 = terms2.getBuckets();
            for (Terms.Bucket bt2 : buckets2) {
                logger.info("---" + bt2.getKeyAsString() + " :: " + bt2.getDocCount());
            }
        }
    }


    /**
     * 聚合分组查询
     * 根据age进行分组
     */
    @Test
    public void aggre1Query2() {
        SearchRequestBuilder srb = client.prepareSearch("java_demo_index").setTypes("bulk_user");
//        srb.setSearchType(SearchType.COUNT);
        TermsAggregationBuilder teamAgg = AggregationBuilders.terms("age_count").field("age");
        srb.addAggregation(teamAgg);
        srb.setSize(1000);
        System.out.println(srb.toString());

        SearchResponse searchResponse = srb.execute().actionGet();
        Aggregations aggregations = searchResponse.getAggregations();
        Map<String, Aggregation> asMap = aggregations.asMap();
        Terms terms = (Terms) asMap.get("age_count");

        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bt : buckets) {
            logger.info(bt.getKeyAsString() + " :: " + bt.getDocCount());
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
    public void facet() {
    }

}