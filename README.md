# Chapter02-SpringBootCloud

此章節需要掛載 MYSQL 、Redis 、Consul 3個 docker image


1.下載 MYSQL image, 帳號密碼為: root/123456
docker run --name dev-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7

2.下載 Redis image
docker run --name redis-lab -p 6379:6379  -v D:/docker/redis/data/:/redis/data/ -d redis --requirepass "123456" `

3.Consul
//建立映射目錄
mkdir -p d:/docker/consul/{conf,data}

//Docker 啟動 Consul container
docker run --name consul -p 8500:8500 -v D:/docker/consul/conf/:/consul/conf/ -v D:/docker/consul/data/:/consul/data/ -d consul

//查看是否建立且運作
dokcer ps

//示範主控台效果
http://127.0.0.1:8500
