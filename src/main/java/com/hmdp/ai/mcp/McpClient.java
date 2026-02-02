package com.hmdp.ai.mcp;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hmdp.ai.config.AiProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class McpClient {

    private final AiProperties properties;

    public McpClient(AiProperties properties) {
        this.properties = properties;
    }

    public JSONArray listTools() {
        JSONObject response = call("tools/list", new JSONObject());
        if (response == null) {
            return new JSONArray();
        }
        JSONObject result = response.getJSONObject("result");
        if (result == null) {
            return new JSONArray();
        }
        return result.getJSONArray("tools");
    }

    public JSONObject callTool(String name, Map<String, Object> arguments) {
        JSONObject params = new JSONObject();
        params.set("name", name);
        params.set("arguments", arguments);
        JSONObject response = call("tools/call", params);
        if (response == null) {
            return new JSONObject();
        }
        JSONObject result = response.getJSONObject("result");
        return result != null ? result : new JSONObject();
    }

    private JSONObject call(String method, JSONObject params) {
        String url = buildUrl(properties.getMcp().getBaseUrl(), properties.getMcp().getApiKey());
        JSONObject payload = new JSONObject();
        payload.set("jsonrpc", "2.0");
        payload.set("id", UUID.randomUUID().toString());
        payload.set("method", method);
        payload.set("params", params);

        HttpResponse response = HttpRequest.post(url)
                .timeout(properties.getMcp().getTimeoutMs())
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .execute();

        if (response.getStatus() != 200) {
            return null;
        }
        String body = response.body();
        if (StrUtil.isBlank(body)) {
            return null;
        }
        return JSONUtil.parseObj(body);
    }

    private String buildUrl(String baseUrl, String apiKey) {
        if (StrUtil.isBlank(baseUrl)) {
            return "";
        }
        if (StrUtil.isBlank(apiKey)) {
            return baseUrl;
        }
        if (baseUrl.contains("key=")) {
            return baseUrl;
        }
        String joiner = baseUrl.contains("?") ? "&" : "?";
        return baseUrl + joiner + "key=" + apiKey;
    }
}
