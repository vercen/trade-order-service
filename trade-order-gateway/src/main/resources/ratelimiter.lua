local currentTime = tonumber(ARGV[1])
local intervalTime = tonumber(ARGV[2])
local requestId = ARGV[3]
local maxRequests = tonumber(ARGV[4])

-- 移除早于当前时间窗口的时间戳
redis.call('ZREMRANGEBYSCORE', 'limit', '-inf', currentTime - intervalTime)

-- 检查当前时间窗口内的时间戳数量
local count = redis.call('ZCOUNT', 'limit', currentTime - intervalTime, currentTime)

if count and count >= maxRequests then
    return 0 -- 超过限制，返回false
else
    -- 将请求时间戳加入漏桶
    redis.call('ZADD', 'limit', currentTime, requestId)
    return 1 -- 在限制范围内，返回true
end
