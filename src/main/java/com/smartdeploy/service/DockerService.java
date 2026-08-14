package com.smartdeploy.service;
import com.smartdeploy.model.ServiceRequest; import org.springframework.stereotype.Service; import java.io.*; import java.util.*; import java.util.concurrent.TimeUnit;
@Service public class DockerService {
 public List<String> list(){return run("docker","ps","-a","--format","{{.Names}}|{{.Status}}|{{.Image}}|{{.Ports}}");}
 public String start(String n){return one(run("docker","start",n));} public String stop(String n){return one(run("docker","stop",n));} public String restart(String n){return one(run("docker","restart",n));} public String remove(String n){return one(run("docker","rm","-f",n));}
 public String add(ServiceRequest r){String n=r.name().replaceAll("[^a-zA-Z0-9_.-]","-"); return one(run("docker","run","-d","--name",n,"-p",r.port()+":8081",r.image()));}
 public String version(){return one(run("docker","--version"));}
 private String one(List<String> x){return String.join(System.lineSeparator(),x);} private List<String> run(String... c){List<String> o=new ArrayList<>(); try{Process p=new ProcessBuilder(c).redirectErrorStream(true).start(); try(BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()))){String s; while((s=b.readLine())!=null)o.add(s);} if(!p.waitFor(30,TimeUnit.SECONDS)){p.destroyForcibly();throw new IllegalStateException("Docker command timed out");} if(p.exitValue()!=0)throw new IllegalStateException(String.join("\n",o)); return o;}catch(Exception e){throw new IllegalStateException("Docker operation failed: "+e.getMessage(),e);}}
}