package ir.mohaymen.querygenerator.api.common.dto;

import ir.mohaymen.querygenerator.domain.schema.enumeration.JoinType;

import java.util.List;
import java.util.Map;

public class JoinRequest {

    private JoinType type;
    private String table;
    private String alias;
    private List<JoinOnRequest> on;
    private String raw;
    private RawSqlRequest fromRaw;
    private Map<String, Object> parameters;

    public JoinRequest() {
    }

    public JoinRequest(JoinType type, String table, String alias, List<JoinOnRequest> on, String raw,
                       RawSqlRequest fromRaw, Map<String, Object> parameters) {
        this.type = type;
        this.table = table;
        this.alias = alias;
        this.on = on;
        this.raw = raw;
        this.fromRaw = fromRaw;
        this.parameters = parameters;
    }

    public JoinType getType() {
        return type;
    }

    public void setType(JoinType type) {
        this.type = type;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<JoinOnRequest> getOn() {
        return on;
    }

    public void setOn(List<JoinOnRequest> on) {
        this.on = on;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public RawSqlRequest getFromRaw() {
        return fromRaw;
    }

    public void setFromRaw(RawSqlRequest fromRaw) {
        this.fromRaw = fromRaw;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
