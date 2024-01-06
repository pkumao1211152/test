import requests
import mysql.connector
import time
from mysql.connector import Error

# 数据库配置
db_config = {
    'host': '127.0.0.1',
    'user': 'root',  # 替换为你的数据库用户名
    'password': 'root',  # 替换为你的数据库密码
    'database': 'tfmal'
}

# 连接数据库
try:
    connection = mysql.connector.connect(**db_config)
    if connection.is_connected():
        db_info = connection.get_server_info()
        print(f"成功连接到MySQL数据库，服务器版本：{db_info}")
        cursor = connection.cursor()
except Error as e:
    print(f"连接错误：{e}")


try:
    cursor.execute("delete from kna_aaa")
    connection.commit()
except Error as e:
    print(f"数据库错误：{e}")
# 设置请求头
headers = {
    'Content-Type': 'application/json',
    'Cookie': 'your_cookie_value_here'  # 替换为您的实际 cookie 值
}

# 发送 POST 请求的函数
def send_post_request(from_value, to_value, limit_value):
    data = [
        {
            "operationName": "TagRecommendedFeedQuery",
            "variables": {
                "tagSlug": "software-engineering",
                "paging": {
                    "from": str(from_value),  # 使用传入的 from 值
                    "to": str(to_value),  # 使用传入的 to 值
                    "limit": limit_value,
                    "source": ""
                }
            },
            "query": "query TagRecommendedFeedQuery($tagSlug: String!, $paging: PagingOptions) {\n  tagFromSlug(tagSlug: $tagSlug) {\n    id\n    viewerEdge {\n      id\n      recommendedPostsFeed(paging: $paging) {\n        items {\n          feedId\n          reason\n          moduleSourceEncoding\n          post {\n            ...StreamPostPreview_post\n            __typename\n          }\n          __typename\n        }\n        pagingInfo {\n          next {\n            from\n            limit\n            source\n            to\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n}\n\nfragment StreamPostPreview_post on Post {\n  id\n  ...StreamPostPreviewContent_post\n    __typename\n}\n\nfragment StreamPostPreviewContent_post on Post {\n  id\n  title\n        ...PostPreviewFooter_post\n     __typename\n}\n\nfragment PostPreviewFooter_post on Post {\n    ...PostPreviewFooterMenu_post\n  __typename\n  id\n}\n\nfragment MultiVoteCount_post on Post {\n  id\n  __typename\n}\n\nfragment PostPreviewFooterMenu_post on Post {\n  creator {\n    __typename\n    id\n  }\n  collection {\n    __typename\n    id\n  }\n    ...ExpandablePostCardOverflowButton_post\n  __typename\n  id\n}\n\nfragment ExpandablePostCardOverflowButton_post on Post {\n  creator {\n    id\n    __typename\n  }\n  ...ExpandablePostCardReaderButton_post\n  __typename\n  id\n}\n\nfragment ExpandablePostCardReaderButton_post on Post {\n  id\n  collection {\n    id\n    __typename\n  }\n  creator {\n    id\n    __typename\n  }\n mediumUrl\n clapCount\n  ...ClapMutation_post\n  __typename\n}\n\nfragment ClapMutation_post on Post {\n  __typename\n  id\n  clapCount\n  ...MultiVoteCount_post\n}\n\n"
        }
    ]

    response = requests.post('https://medium.com/_/graphql', json=data, headers=headers)

    if response.status_code == 200:
        print(f'成功获取数据，from={from_value}, to={to_value}, limit={limit_value}：')
        parsed_response = response.json()
        
        # 提取posts并插入数据库
        
        posts = parsed_response[0]['data']['tagFromSlug']['viewerEdge']['recommendedPostsFeed']['items']
        time.sleep(0)
        for post in posts:
            post_data = post['post']
            post_id = post_data['id']
            title = post_data.get('title', '')
            clapCount = post_data.get('clapCount', 0)
            mediumUrl = post_data.get('mediumUrl', '')
            if clapCount > 3000:
                print(f"Inserting Post ID: {post_id}, Title: {title}, ClapCount: {clapCount}")
                try:
                    cursor.execute("INSERT INTO kna_aaa (post_id, title, clapCount,mediumUrl) VALUES (%s, %s, %s, %s)", (post_id, title, clapCount,mediumUrl))
                    connection.commit()
                    
                except Error as e:
                    print(f"数据库错误：{e}")
          #  else:
              #  print(f"Post ID: {post_id} has ClapCount: {clapCount}, which is not greater than 3000. Skipping insert.")
                
    else:
        print(f'请求失败，状态码：{response.status_code}')

# 循环发送 POST 请求
from_value = 0
to_value = 25
limit_value = 25
for _ in range(500):  # 假设需要循环5次
    send_post_request(from_value, to_value, limit_value)
    from_value += 25
    to_value += 25

try:
    cursor.execute("TRUNCATE TABLE source_data")
    cursor.execute("INSERT INTO source_data (post_id, title, clapCount, mediumUrl) SELECT post_id, title, clapCount, mediumUrl FROM kna_aaa")
    connection.commit()
except Error as e:
    print(f"数据库错误：{e}")    
