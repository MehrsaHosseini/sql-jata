package ir.mohaymen.querygenerator.api.rest;

import ir.mohaymen.querygenerator.api.rest.config.QueryGeneratorConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QueryGeneratorController.class)
@Import({QueryGeneratorConfiguration.class, QueryGeneratorExceptionHandler.class})
class QueryGeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void selectEndpointGeneratesNamedSql() throws Exception {
        mockMvc.perform(post("/api/queries/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "columns": [
                                    { "name": "E.ID" },
                                    { "name": "E.NAME" }
                                  ],
                                  "from": { "table": "EMPLOYEE", "alias": "E" },
                                  "joins": [
                                    {
                                      "type": "LEFT",
                                      "table": "DEPT",
                                      "alias": "D",
                                      "on": [{ "left": "E.DEPT_ID", "right": "D.ID" }]
                                    }
                                  ],
                                  "where": {
                                    "op": "and",
                                    "conditions": [
                                      { "op": "eq", "column": "E.STATUS", "value": "ACTIVE" },
                                      { "op": "isNull", "column": "E.DELETED_AT" }
                                    ]
                                  },
                                  "orderBy": [{ "column": "E.ID", "direction": "DESC" }],
                                  "limit": 10
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.query").value(
                        "SELECT \"E\".\"ID\", \"E\".\"NAME\" FROM \"EMPLOYEE\" \"E\" "
                                + "LEFT OUTER JOIN \"DEPT\" \"D\" ON \"E\".\"DEPT_ID\" = \"D\".\"ID\" "
                                + "WHERE (\"E\".\"STATUS\" = :p1 AND \"E\".\"DELETED_AT\" IS NULL) "
                                + "ORDER BY \"E\".\"ID\" DESC FETCH NEXT :p2 ROWS ONLY"))
                .andExpect(jsonPath("$.parameters.p1").value("ACTIVE"))
                .andExpect(jsonPath("$.parameters.p2").value(10));
    }

    @Test
    void insertEndpoint() throws Exception {
        mockMvc.perform(post("/api/queries/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "table": "EMPLOYEE",
                                  "columns": ["NAME", "SALARY"],
                                  "values": ["ALI", 7500]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.query").value(
                        "INSERT INTO \"EMPLOYEE\" (\"NAME\", \"SALARY\") VALUES (:p1, :p2)"))
                .andExpect(jsonPath("$.parameters.p1").value("ALI"))
                .andExpect(jsonPath("$.parameters.p2").value(7500));
    }

    @Test
    void invalidPredicateReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/queries/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "from": { "table": "EMPLOYEE" },
                                  "where": { "op": "unknown" }
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("bad_request"));
    }
}
