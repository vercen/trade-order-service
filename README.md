# 交易订单服务

**项目简介**

交易订单服务是一个基于Java的项目，旨在实现订单相关功能，并提供可靠的负载均衡、限流、超时处理等特性。项目包含两个独立模块：trade-order-gateway和trade-order-api。trade-order-gateway负责请求转发和负载均衡，而trade-order-api则承载核心的订单功能。

**前提条件**

在运行服务之前，请确保满足以下先决条件：

- Java 8
- MySQL数据库
- Redis服务器
* 项目开发方式
  - 分支开发，发布时合并主干
  - 环境隔离（profile：local、dev、uat、prod）

### 模块

* biz：公共业务模块（供其它模块调用）<br/>
* biz-base:基础业务 <br/>
* biz-req-trace:请求链路跟踪<br/>
* biz-glue:<br/>
* trade-order-api：业务代码 <br/>
* trade-order-gateway：路由服务 <br/>

**项目结构**

该项目按照以下结构组织：

1. **trade-order-gateway**：作为请求转发和负载均衡的模块，trade-order-gateway还实现了分布式锁，以确保在并发情况下的请求唯一性和数据一致性。

2. **trade-order-api**：作为核心模块，trade-order-api提供与订单相关的功能，包括查询订单详情、查询机房名称、抵扣券抵扣等功能。

**运行服务**

以下是运行服务的步骤：

1. 克隆代码库：

```bash
git clone http://120.92.88.48/${student_feishu_name}/trade-order-service.git
```

2. 安装依赖并打包项目：

```bash
cd trade-order-service
mvn clean package -Dmaven.test.skip=true -Pprod
```

3. 启动服务：

对于本地环境：

```bash
sh ./bin/start_server.sh -e=local
```

对于生产环境：

```bash
sh ./bin/start_server.sh -e=prod
```

**端点和API**

1. **请求转发(负载均衡)**

  - trade-order-gateway模块负责处理请求转发和向trade-order-api服务的负载均衡。
  - 在转发请求之前，trade-order-gateway还会实现分布式锁，避免并发情况下的重复请求和数据不一致问题。
  - 使用随机负载均衡算法，确保请求均匀分布到trade-order-api服务的各个实例中。

2. **查询订单详情**

  - 端点：/online/queryOrderInfo?id={orderId}
  - 该端点从MySQL数据库获取订单详情。
  - 使用了Hikari连接池，以确保数据库连接的可靠性。
  - redis缓存订单详情，避免重复查询数据库。


3. **查询机房名称**

  - 端点：/online/queryRegionName?regionId={regionId}
  - 该端点通过查询第三方接口，根据机房ID获取机房名称。
  - 响应格式：参考需求中提供的响应结构。

4. **抵扣券抵扣**

  - 端点：/online/voucher/deduct
  - HTTP方法：POST
  - 该端点允许用户将抵扣券应用于订单并执行抵扣操作。
  - 该端点使用Redis实现了分布式锁，避免并发情况下的重复请求和数据不一致问题。

5. **基于Redis的限流（漏桶算法）**

  - 端点：/online/listUpstreamInfo
  - 该端点使用Redis实现了漏桶算法进行限流。
  - 响应格式：参考需求中提供的响应结构。

6. **简单的链路跟踪实现**
  - 服务包含链路跟踪功能，使用X-KSY-REQUEST-ID头标识用户请求，并通过该标识串联调用链。

