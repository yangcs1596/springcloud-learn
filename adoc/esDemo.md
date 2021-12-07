###查询
```
# POST
# http://192.168.72.145:9200/cloudarmor_asset_app_risk_index/_search
{
    "query": { 
    },
    "from": 0,
    "size": 4,
    "collapse": {
        "field": "assetName assetVersion"
    },
    "_source": {
        "includes": [
            "assetName",
            "assetVersion"
        ]
    }
}
```

###更新
```
# POST
#ip/cloudarmor_asset_app_risk_index/_update_by_query
{
	"query": {
        "bool": { 
            "filter": {
                "terms": {
                    "serverId": [2508]
                }
            }
        }
    },
    "script" : {
        "inline": "ctx._source.new_field = params.count", 
        "params" : {
            "count" : 3
        }
    }
}
```


###新增
```
# POST
# ip/cloudarmor_asset_app_risk_index/AssetAppRiskInfo
{
	"serverId": 2508,
	"containerInfoId": 36,
	"containerName": "容器",
	"userId": 1,
	"riskName":"CVE_Zksks",
	"riskDetailName": "nginxcesium数据",
	"riskLevel":4,
	"repairAffect":"重启系统",
	"riskType":"未知",
	"assetType":4,
	"assetName":"nvinx",
	"assetVersion":"1.0.0",
	"assetPath":"/usr/local/rc",
	"discoveryTime": "2021-12-01",
	"state":5,
	"insertTime":"2021-12-01"
}

# PUT
# /cloudarmor_asset_app_risk_index/AssetAppRiskInfo/_mapping 
# 新增字段
{
    "properties": {
        "riskId": {
            "type": "text"
        }
    }
}
```
