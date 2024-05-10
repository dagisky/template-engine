package com.aa.ticketing;

import com.aa.ticketing.exception.TemplateRendererException;
import com.aa.ticketing.util.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JsonTemplateRenderer implements TemplateRenderer{

    private String template;
    private Map<String, TemplateRenderer> subTemplates;

    public JsonTemplateRenderer(String file){
        this.template = TemplateUtil.loadTemplate(file);
        this.subTemplates = new HashMap<>();
        buildRenderGraph();
    }

    private void buildRenderGraph(){
        try {
            Pattern sub_template_pattern = Pattern.compile("\\{\\{>.*?}}");
            Matcher sub_template_matcher = sub_template_pattern.matcher(this.template);
            while (sub_template_matcher.find()) {
                // Generate a new UUID for each match and replace
                String replacement = UUID.randomUUID().toString();
                String sub_template = sub_template_matcher.group().replace("{{>", "").replace("}}","");  // returns interpolation ie {{some.field}}
                this.subTemplates.put(replacement, new JsonTemplateRenderer(sub_template));
                this.template = sub_template_matcher.replaceFirst(replacement);
            }
        } catch (Exception e) {
            log.warn("{} thrown injecting {}", e.getClass(), this.template);
            throw new TemplateRendererException(String.format("%s thrown injecting %s", e.getClass(), this.template));
        }
    }


    @Override
    public String render(Trie trie) {
        // do a dfs on the template tree and render with the given trie ds

        Map<String, String> params = trie.getParams();
        String target = "";
        try{
            Pattern param_pattern = Pattern.compile("\\{\\{[^>].*?}}");
            Matcher param_matcher = param_pattern.matcher(this.template);
            while (param_matcher.find()) {
                target = param_matcher.group().replace("{{","").replace("}}", "").trim();  // returns interpolation ie {{some.field}}.. find the field that needs to be poulated  and populate it with the
                this.template = param_matcher.replaceFirst(params.get(target));
                param_matcher = param_pattern.matcher(this.template);
            }
        }catch (Exception e){
            log.warn("{} thrown injecting {}", e.getClass(), target);
            throw new TemplateRendererException(String.format("%s thrown injecting %s", e.getClass(), target));
        }

        JSONObject jsonObject = new JSONObject(this.template);
        for (String key: jsonObject.keySet()) {
            String uuid = jsonObject.getString(key);
            if(subTemplates.containsKey(uuid)){
                jsonObject.put(key, subTemplates.get(uuid).render(trie.getTrie(key)));
                this.template = jsonObject.toString();
            }
        }
        return this.template;
    }
}
