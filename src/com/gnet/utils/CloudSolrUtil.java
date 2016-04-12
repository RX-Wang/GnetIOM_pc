/*   
 * Copyright (c) 2014-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.gnet.utils;

/*import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;*/

/**
 * <table width="100%" border="1px">
 * <tr>
 * <td width="20%">作者</td><td width="80%" colspan="2">sam</td>
 * </tr>
 * <tr>
 * <td width="20%">版本</td><td width="80%" colspan="2">v1.0</td>
 * </tr>
 * <tr>
 * <td width="20%">创建日期</td><td width="80%" colspan="2">2013-06-24</td>
 * </tr>
 * <tr>
 * <td width="100%" colspan="3">修订记录:</td>
 * <tr>
 * <td width="20%">修改日期</td><td width="20%">修改人</td><td width="60%">修改记录</td>
 * </tr>
 * <tr>
 * <td width="20%">-------</td><td width="20%">-------</td><td width="60%">--------------</td>
 * </tr>
 * <tr>
 * <td width="20%">描述信息</td><td width="80%" colspan="2">分布式solr的操作api</td>
 * </tr>
 * </tr>
 * </table>
 */
public class CloudSolrUtil {

	/*private static CloudSolrServer cloudSolrServer;

	*//**
	 * 描述信息:初始化分布式solr
	 * 
	 * 作者 sam
	 * 
	 * @param psHost
	 * 				zookeeper的主机地址
	 * @return
	 * 				CloudSolrServer对象
	 *//*
	private static synchronized CloudSolrServer getCloudSolrServer(
			final String psHost) {
		if (cloudSolrServer == null) {
			try {
				cloudSolrServer = new CloudSolrServer(psHost);
			} catch (MalformedURLException e) {
				System.out
						.println("The URL of zkHost is not correct!! Its form must as below:\n zkHost:port");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cloudSolrServer;
	}

	*//**
	 * 描述信息:向分布式solr添加索引
	 * 
	 * 作者 sam
	 *//*
	private void addIndex() {
		try {
			SolrInputDocument doc1 = new SolrInputDocument();
			doc1.addField("id", "1");
			doc1.addField("name", "张民");

			SolrInputDocument doc2 = new SolrInputDocument();
			doc2.addField("id", "2");
			doc2.addField("name", "刘俊");

			SolrInputDocument doc3 = new SolrInputDocument();
			doc3.addField("id", "3");
			doc3.addField("name", "刘俊2");

			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			docs.add(doc1);
			docs.add(doc2);
			docs.add(doc3);

			cloudSolrServer.add(docs);
			cloudSolrServer.commit();

		} catch (SolrServerException e) {
			System.out.println("Add docs Exception !!!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unknowned Exception!!!!!");
			e.printStackTrace();
		}

	}

	*//**
	 * 描述信息:在分布式solr中查找数据
	 * 
	 * 作者 sam
	 * 
	 * @param psKey
	 * 				查询的关键字
	 *//*
	public void search(String psKey) {
		SolrQuery query = new SolrQuery();
		query.setQuery(psKey);

		try {
			QueryResponse response = cloudSolrServer.query(query);
			SolrDocumentList docs = response.getResults();

			System.out.println("文档个数：" + docs.getNumFound());
			System.out.println("查询时间：" + response.getQTime());

			for (SolrDocument doc : docs) {
				String name = (String) doc.getFieldValue("name");
				String id = (String) doc.getFieldValue("id");
				System.out.println("id: " + id);
				System.out.println("name: " + name);
				System.out.println();
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unknowned Exception!!!!");
			e.printStackTrace();
		}
	}

	*//**
	 * 描述信息:删除所有索引
	 * 
	 * 作者 sam
	 * 
	 *//*
	public void deleteAllIndex() {
		try {
			cloudSolrServer.deleteByQuery("*:*");// delete everything!
			cloudSolrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unknowned Exception !!!!");
			e.printStackTrace();
		}
	}

	private static void main(String[] args) {
		final String zkHost = "192.168.4.55:9080";
		final String defaultCollection = "ibd_connection";
		final int zkClientTimeout = 20000;
		final int zkConnectTimeout = 10000;

		CloudSolrServer cloudSolrServer = getCloudSolrServer(zkHost);
		System.out.println("The Cloud SolrServer Instance has benn created!");

		cloudSolrServer.setDefaultCollection(defaultCollection);
		cloudSolrServer.setZkClientTimeout(zkClientTimeout);
		cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);

		cloudSolrServer.connect();
		System.out.println("The cloud Server has been connected !!!!");

		ZkStateReader zkStateReader = cloudSolrServer.getZkStateReader();
		ClusterState cloudState = zkStateReader.getClusterState();
		System.out.println(cloudState);

		// 测试实例！
		CloudSolrUtil test = new CloudSolrUtil();
		System.out.println("测试添加index！！！");
		// 添加index
		test.addIndex();

		System.out.println("测试查询query！！！！");
		test.search("id:*");

		System.out.println("测试删除！！！！");
		test.deleteAllIndex();
		System.out.println("删除所有文档后的查询结果：");
		test.search("*:*");

		// release the resource
		cloudSolrServer.shutdown();

	}*/
}
