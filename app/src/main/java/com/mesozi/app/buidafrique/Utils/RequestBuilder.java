package com.mesozi.app.buidafrique.Utils;

import android.util.Log;

import org.json.JSONArray;
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
        params.put("db", "buildafrique_dev_new");
        params.put("login", username);
        params.put("password", pasword);

        jsonObject.put("params", params);
        jsonObject.put(ID, LOGIN_ID);
        return jsonObject;

    }

    public static JSONObject registerAffiliateJson(String name, String email, String referral_code, String occupation, String phone, String city) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        JSONObject values = new JSONObject();
        values.put("name", name);
        values.put("email", email);
        values.put("referral_code", referral_code);
        values.put("occupation", occupation);
        values.put("phone", phone);
        values.put("city", city);
        params.put("values", values);

        jsonObject.put("params", params);
        jsonObject.put("id", 123);

        return jsonObject;
    }

    public static JSONObject createMessge(String subject, String message) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("db", "buildafrique_dev_new");
        params.put("subject", subject);
        params.put("message", message);

        jsonObject.put("params", params);
        jsonObject.put(ID, LOGIN_ID);
        return jsonObject;

    }

    public static JSONObject fetchMessagesRequest() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);
        JSONObject params = new JSONObject();
        params.put("model", "mail.channel");
        params.put("method", "search_read");

        JSONArray args = new JSONArray();
        params.put("args", args);

        JSONObject kwargs = new JSONObject();
        params.put("kwargs", kwargs);

        jsonObject.put("params", params);
        jsonObject.put(ID, LOGIN_ID);

        return jsonObject;
    }

    public static JSONObject salesOrders() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("model", "sale.order");
        params.put("method", "fetch_new_sales");

        JSONArray args = new JSONArray();
        params.put("args", args);

        JSONObject kwargs = new JSONObject();
        JSONArray ids = new JSONArray();
        kwargs.put("existing_ids", ids);
        kwargs.put("limit", 100);
        params.put("kwargs", kwargs);

        jsonObject.put("params", params);
        jsonObject.put(ID, 123);

        return jsonObject;
    }

    public static JSONObject readSalesOrders() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("model", "sale.order");
        params.put("method", "search_read");

        String args = "[[[\"user_id\",\"=\",33],[\"id\", \"not in\", [241]]]]";
        params.put("args", args);

        JSONObject kwargs = new JSONObject();
        JSONArray fields = new JSONArray();
        kwargs.put("fields", fields);
        kwargs.put("offset", 0);
        kwargs.put("limit", 100);
        kwargs.put("order", "id");
        params.put("kwargs", kwargs);

        jsonObject.put("params", params);
        jsonObject.put(ID, 123);

        return jsonObject;
    }

    public static JSONObject salesRequest() throws JSONException {
        String request = "\n" +
                "{\n" +
                "    \"jsonrpc\":\"2.0\",\n" +
                "    \"method\":\"call\",\n" +
                "    \"params\": {\n" +
                "        \"model\":\"sale.order\",\n" +
                "        \"method\":\"search_read\",\n" +
                "        \"args\":[[[\"user_id\",\"=\",33],[\"id\", \"not in\", [241]]]],\n" +
                "        \"kwargs\": {\n" +
                "            \"fields\": [], \"offset\": 0, \"limit\": 100, \"order\": \"id\"\n" +
                "        }\n" +
                "    }\n" +
                "    ,\n" +
                "    \"id\":123\n" +
                "}";
        return new JSONObject(request);
    }

    public static JSONObject customersObject() throws JSONException {
        String request = "\n" +
                "\n" +
                "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":{\n" +
                "    \"model\": \"res.partner\",\n" +
                "    \"method\":\"search_read\",\n" +
                "      \"args\":[[[\"active\", \"=\", true],[\"customer\", \"=\", true]]],\n" +
                "    \"kwargs\":\n" +
                "      {\n" +
                "      \t\"fields\":[\"company\",\"customer\",\"display_name\",\"partner_id\",\"id\",\"name\",\"email\",\"phone\",\"street\",\"city\",\"zip\"],\n" +
                "        \"offset\":0,\n" +
                "        \"limit\":100,\n" +
                "        \"order\":\"id\"\n" +
                "      }\n" +
                "  },\n" +
                "  \"id\":123\n" +
                "}";
        return new JSONObject(request);
    }

    public static JSONObject productsObject() throws JSONException {
        String request = "\n" +
                "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":{\n" +
                "    \"model\": \"product.product\",\n" +
                "    \"method\":\"search_read\",\n" +
                "      \"args\":[[[\"active\", \"=\", true]]],\n" +
                "    \"kwargs\":\n" +
                "      {\n" +
                "      \t\"fields\":[\"display_name\",\"id\",\"name\"],\n" +
                "        \"offset\":0,\n" +
                "        \"limit\":100,\n" +
                "        \"order\":\"id\"\n" +
                "      }\n" +
                "  },\n" +
                "  \"id\":123\n" +
                "}";

        return new JSONObject(request);
    }

    public static JSONObject inboxObject() throws JSONException {
        String request = "\n" +
                "\n" +
                "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":{\n" +
                "    \"model\": \"mail.message\",\n" +
                "    \"method\":\"search_read\",\n" +
                "      \"args\":[[]],\n" +
                "    \"kwargs\":\n" +
                "      {\n" +
                "      \t\"fields\":[\"company\",\"customer\",\"display_name\",\"message\",\"id\",\"name\",\"email\",\"email_from\",\"reply_to\",\"attachment_ids\",\"subject\",\"date\",\"starred\",\"body\"],\n" +
                "        \"offset\":0,\n" +
                "        \"limit\":100,\n" +
                "        \"order\":\"id\"\n" +
                "      }\n" +
                "  },\n" +
                "  \"id\":123\n" +
                "}";

        return new JSONObject(request);
    }

    public static JSONObject createLeads(String displayName, String name, String email, String addressName, String emailFrom) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("model", "crm.lead");
        params.put("method", "create");

        JSONArray args = new JSONArray();
        JSONObject arg = new JSONObject();
        arg.put("write_uid", 1);
        arg.put("display_name", displayName);
        arg.put("name", name);
        arg.put("partner_address_email", email);
        arg.put("partner_address_name", addressName);
        arg.put("email_from", emailFrom);
        args.put(arg);
        params.put("args", args);

        JSONObject kwargs = new JSONObject();
        params.put("kwargs", kwargs);

        jsonObject.put("params", params);
        jsonObject.put(ID, 123);

        return jsonObject;
    }

    public static JSONObject readLeads() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\": {\n" +
                "    \"model\":\"crm.lead\",\n" +
                "    \"method\":\"search_read\",\n" +
                "    \"args\":[],\n" +
                "    \"kwargs\" : {}\n" +
                "  },\n" +
                "  \"id\":1969558901\n" +
                "}";

        return new JSONObject(request);
    }

}
