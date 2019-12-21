package com.ff.community.provider;

import com.alibaba.fastjson.JSON;
import com.ff.community.dto.AccessTokenDTO;
import okhttp3.*;

import java.io.IOException;

public class GitHubProvider {
    public String getAcessToken(AccessTokenDTO accessTokenDTO) throws IOException {
          MediaType JSON
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSON(accessTokenDTO));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }

    }
}
