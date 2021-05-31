package cn.coonu.ddns.common.scheduler;

import cn.coonu.ddns.common.Alidns;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class SchedulerTasks {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerTasks.class);

    @Autowired
    private Alidns alidns;

    /**
     * 此任务需要保证正常获取IP(无法保证), 阿里云接口权限要给到
     * <p>
     * 单位毫秒, 300000 - 5分钟
     *
     * @throws Exception 异常
     */
    @Scheduled(fixedRate = 300000)
    public void task() throws Exception {
        LOG.debug("任务开始---------------------------------------------");

        String ip = alidns.getOwnIp();
        if (ip.split("\\.").length != 4) {
            LOG.debug("获取IP失败, 获取到的信息为: {}", ip);
            return;
        }

        List<DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord> records = alidns.getDnsRecords(20L).getDomainRecords().getRecord();

        int success = 0, fail = 0;
        for (DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord record : records) {
            try {
                if (ip.equals(record.getValue())) {
                    LOG.debug("IP未改变, 主机记录: {} !", record.getRR());
                    continue;
                } else if (record.getValue().matches("192\\.168\\..+")) {
                    LOG.debug("为内网IP, 主机记录: {} !", record.getRR());
                    continue;
                }
                alidns.updateDnsRecord(record, ip);
                success++;
            } catch (Exception e) {
                fail++;
            }
        }

        LOG.debug("共{}条, 成功{}条, 失败{}条, 不用修改{}条!", records.size(), success, fail, records.size() - success - fail);

    }

}
