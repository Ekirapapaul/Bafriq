package com.mesozi.app.buidafrique.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ekirapa on 7/10/18 .
 */
public class RequestBuilder {
    private static final String JSON_VERS = "2.0";
    private static final String METHOD_CALL = "call";
    private static final int LOGIN_ID = 1969558901;
    private static final String ID = "id";

    public static JSONObject LoginRequest(String username, String pasword) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("db", "buildafrique_dev");
        params.put("login", username);
        params.put("password", pasword);

        jsonObject.put("params", params);
        jsonObject.put(ID, LOGIN_ID);
        return jsonObject;

    }
}
