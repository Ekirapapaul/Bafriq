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
    private static final String DB = "buildafrique_dev_new";
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

    public static JSONObject registerAffiliateJson(String name, String email, String referral_code, String phone, String password, String occupation, String location) throws JSONException {
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
        values.put("password", password);
        values.put("city", location);
        params.put("values", values);

        jsonObject.put("params", params);
        jsonObject.put("id", 123);

        return jsonObject;
    }

    public static JSONObject createMessge(String subject, String message, String email_from, String name) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("model", "mail.message");
        params.put("method", "create");

        JSONArray args = new JSONArray();
        JSONObject arg = new JSONObject();
        arg.put("write_uid", 1);
        arg.put("subject", subject);
        arg.put("name", name);
        arg.put("partner_address_email", email_from);
        arg.put("partner_address_name", name);
        arg.put("email_from", email_from);
        args.put(arg);
        params.put("args", args);

        JSONObject kwargs = new JSONObject();
        params.put("kwargs", kwargs);

        jsonObject.put("params", params);
        jsonObject.put(ID, 123);

        return jsonObject;

    }

    public static JSONObject getDashboard() throws JSONException {
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":\n" +
                "  {\n" +
                "  },\n" +
                "  \"id\":1969558901\n" +
                "}";

        return new JSONObject(request);
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

    public static JSONObject customersObject(String uid) throws JSONException {
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\": {\n" +
                "    \"model\":\"res.partner\",\n" +
                "    \"method\":\"search_read\",\n" +
                "    \"args\":[[[\"user_id\",\"=\", " + uid + "]]],\n" +
                "    \"kwargs\" : {\"fields\":[\"name\",\"phone\",\"email\"]}\n" +
                "  },\n" +
                "  \"id\":1969558901\n" +
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
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":{\n" +
                "    \"model\": \"mail.message\",\n" +
                "    \"method\":\"fetch_messages\",\n" +
                "      \"args\":[],\n" +
                "    \"kwargs\":\n" +
                "      {\n" +
                "        \"existing_ids\":[],\n" +
                "        \"limit\":100\n" +
                "      }\n" +
                "  },\n" +
                "  \"id\":123\n" +
                "}";

        return new JSONObject(request);
    }

    public static JSONObject inboxObject(String existing_ids) throws JSONException {
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":{\n" +
                "    \"model\": \"mail.message\",\n" +
                "    \"method\":\"fetch_messages\",\n" +
                "      \"args\":[],\n" +
                "    \"kwargs\":\n" +
                "      {\n" +
                "        \"existing_ids\":[ " + existing_ids + "],\n" +
                "        \"limit\":100\n" +
                "      }\n" +
                "  },\n" +
                "  \"id\":123\n" +
                "}";

        return new JSONObject(request);
    }

    public static JSONObject readMessage() throws JSONException {
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":\n" +
                "  {\n" +
                "  },\n" +
                "  \"id\": 123\n" +
                "}";

        return new JSONObject(request);
    }


    public static JSONObject createLeads(String displayName, String name, String description, String email, String addressName, String emailFrom) throws JSONException {
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
        arg.put("description", description);
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

    public static JSONObject createCustomer(String displayName, String name, String email, String addressName, String mobile, String user_id) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("model", "res.partner");
        params.put("method", "create");

        JSONArray args = new JSONArray();
        JSONObject arg = new JSONObject();
        arg.put("write_uid", 1);
        arg.put("display_name", displayName);
        arg.put("email", email);
        arg.put("name", name);
        arg.put("mobile", mobile);
        arg.put("user_id", Integer.valueOf(user_id));
//        arg.put("partner_address_email", email);
//        arg.put("partner_address_name", addressName);
        args.put(arg);
        params.put("args", args);

        JSONObject kwargs = new JSONObject();
        params.put("kwargs", kwargs);

        jsonObject.put("params", params);
        jsonObject.put(ID, 123);

        return jsonObject;
    }

    public static JSONObject getShareMessage() throws JSONException {
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":\n" +
                "  {\n" +
                "  },\n" +
                "  \"id\":1969558901\n" +
                "}";

        return new JSONObject(request);
    }
    public static JSONObject getRefferalMessage() throws JSONException {
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\":\n" +
                "  {\n" +
                "  },\n" +
                "  \"id\":1969558901\n" +
                "}";

        return new JSONObject(request);
    }

    public static JSONObject readLeads() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String request = "{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\",\n" +
                "  \"params\": {\n" +
                "    \"model\":\"crm.lead\",\n" +
                "    \"method\":\"fetch_new_leads\",\n" +
                "    \"args\":[],\n" +
                "    \"kwargs\" : {}\n" +
                "  },\n" +
                "  \"id\":1969558901\n" +
                "}";

        return new JSONObject(request);
    }


    public static JSONObject redeemCommission(int amount, String payment_method) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        //params.put("db", DB);
        params.put("amount", amount);
        params.put("payment_method", payment_method);

        jsonObject.put("params", params);
        jsonObject.put(ID, 123);
        return jsonObject;

    }

    public static JSONObject redeemLoyalty() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        //params.put("db", DB);


        jsonObject.put("params", params);
        jsonObject.put(ID, 123);
        return jsonObject;

    }

    public static JSONObject redeemBonus(int amount, String payment_method) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        //params.put("db", DB);
        params.put("amount", amount);
        params.put("payment_method", payment_method);

        jsonObject.put("params", params);
        jsonObject.put(ID, 123);
        return jsonObject;

    }

    public static JSONObject getCode(String email, String phone) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("login", email);
        params.put("phone", phone);
        jsonObject.put("params", params);

        return jsonObject;
    }

    public static JSONObject changePassword(String code, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", JSON_VERS);
        jsonObject.put("method", METHOD_CALL);

        JSONObject params = new JSONObject();
        params.put("login", code);
        params.put("phone", password);
        jsonObject.put("params", params);

        return jsonObject;
    }

}
