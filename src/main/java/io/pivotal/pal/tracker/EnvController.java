package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private final String port;
    private final String memory_limit;
    private final String cf_instance_index;
    private final  String cf_instance_addr;


    public EnvController(
            @Value("${PORT: NOT SET}") String PORT,
            @Value("${MEMORY_LIMIT: NOT SET}")String MEMORY_LIMIT,
            @Value("${CF_INSTANCE_INDEX: NOT SET}")String CF_INSTANCE_INDEX,
            @Value("${CF_INSTANCE_ADDR: NOT SET}")String CF_INSTANCE_ADDR
    ) {
        this.port = PORT;
        this.memory_limit = MEMORY_LIMIT;
        this.cf_instance_index = CF_INSTANCE_INDEX;
        this.cf_instance_addr = CF_INSTANCE_ADDR;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("PORT", port);
        tempMap.put("MEMORY_LIMIT", memory_limit);
        tempMap.put("CF_INSTANCE_INDEX", cf_instance_index);
        tempMap.put("CF_INSTANCE_ADDR", cf_instance_addr);

        return tempMap;
    }
}
