package org.example;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author luhao
 */
@Mojo(name = "hello", defaultPhase = LifecyclePhase.INITIALIZE)
public class ExampleMojo extends AbstractMojo {

    @Parameter(property = "welcome", defaultValue = "Hello, world")
    private String welcome;

    @Parameter(property = "project", readonly = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        project.getProperties().put("welcome", welcome);

        try {
            printResources();
        } catch (Exception e) {
            throw new MojoExecutionException("read resource dir fail ", e);
        }

        getLog().info(welcome);

    }

    private void printResources() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<Resource> resources = project.getResources();
        for (Resource resource : resources) {
            String directory = resource.getDirectory();
            try (Stream<Path> list = Files.list(Paths.get(directory))){
                List<Path> files = list.collect(Collectors.toList());
                sb.append(String.format("resource %s (size=%d):\n", directory, files.size()));
                files.forEach(it -> sb.append("\t\t-").append(it.getFileName()).append("\n"));
            }
        }
        getLog().info(sb);
    }
}