package org.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @author luhao
 */
@Mojo(name = "hello", defaultPhase = LifecyclePhase.INITIALIZE)
public class ExampleMojo extends AbstractMojo {

    @Parameter(property = "welcome", defaultValue = "Hello, world")
    private String welcome;

    @Parameter(property = "project", readonly = true)
    private MavenProject mavenProject;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        mavenProject.getProperties().put("welcome", welcome);
        getLog().info(welcome);

    }
}