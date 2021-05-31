package cn.coonu.ddns.common;

import com.aliyun.alidns20150109.Client;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.UpdateDomainRecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * dns相关api
 *
 * @author Jack Wang
 */
@Component
public class Alidns {

    @Autowired
    private Client client;

    @Autowired
    RestTemplate template;

    /**
     * 获取阿里云dns解析列表
     *
     * @param pageSize 无关紧要的参数, 随意填, 但是为了性能, 最好填比解析记录大一点的数值, 比如20, 我的解析记录一般只有几条;
     * @return 获取到的解析记录信息
     * @throws Exception key过期或权限不足等原因就抛异常;
     */
    public DescribeDomainRecordsResponseBody getDnsRecords(Long pageSize) throws Exception {
        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest()
                .setDomainName("lezz.com.cn")
                .setPageSize(pageSize)
                .setType("A");
        DescribeDomainRecordsResponseBody responseBody = client.describeDomainRecords(request).getBody();
        if (responseBody.getTotalCount() > responseBody.getPageSize()) {
            return getDnsRecords(responseBody.getTotalCount());
        }
        return responseBody;
    }

    /**
     * 修改dns解析记录
     *
     * @param oldRecord 旧记录
     * @throws Exception 修改失败抛异常
     */
    public void updateDnsRecord(DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord oldRecord, String ip) throws Exception {
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest()
                .setRecordId(oldRecord.getRecordId())
                .setRR(oldRecord.getRR())
                .setType(oldRecord.getType())
                .setValue(ip);
        client.updateDomainRecord(request);
    }

    /**
     * 获取本机公网IP
     *
     * @return IP
     */
    public String getOwnIp() {
        String url = "https://v6r.ipip.net/";
        return template.getForObject(url, String.class);
    }

}
