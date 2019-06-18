package cn.edu.pku.hcst.kincoder.pattern.miner;

import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import lombok.Data;

import java.util.List;

@Data
public class PatternMiningConfig implements PatternConfig {
    private String uri;
    private String username;
    private String password;

    private String clientCodeDir;

    private Integer nodeSizeLimit = null;
    private List<String> dfgNodeFilters = List.of();
    private List<String> sourceCodeDirs;

    private boolean debug = false;

}
