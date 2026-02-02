package com.hmdp.ai.llm;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hmdp.ai.config.AiProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DeepSeekClient {

    private final AiProperties properties;

    public DeepSeekClient(AiProperties properties) {
        this.properties = properties;
    }

    public String chat(List<Map<String, String>> messages) {
        String url = buildUrl(properties.getLlm().getApiBase());
        JSONObject payload = new JSONObject();
        payload.set("model", properties.getLlm().getModel());
        payload.set("messages", messages);
        payload.set("temperature", 0.4);

        HttpResponse response = HttpRequest.post(url)
                .timeout(properties.getLlm().getTimeoutMs())
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + properties.getLlm().getApiKey())
                .body(payload.toString())
                .execute();

        if (response.getStatus() != 200) {
            return "";
        }
        String body = response.body();
        if (StrUtil.isBlank(body)) {
            return "";
        }
        JSONObject json = JSONUtil.parseObj(body);
        JSONArray choices = json.getJSONArray("choices");
        if (choices == null || choices.isEmpty()) {
            return "";
        }
        JSONObject first = choices.getJSONObject(0);
        if (first == null) {
            return "";
        }
        JSONObject message = first.getJSONObject("message");
        if (message == null) {
            return "";
        }
        return message.getStr("content", "");
    }

    private String buildUrl(String apiBase) {
        if (StrUtil.isBlank(apiBase)) {
            return "";
        }
        if (apiBase.endsWith("/")) {
            return apiBase + "chat/completions";
        }
        return apiBase + "/chat/completions";
    }
}
