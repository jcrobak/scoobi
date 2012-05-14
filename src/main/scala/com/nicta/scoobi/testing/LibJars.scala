package com.nicta.scoobi.testing

import java.net.{URLClassLoader, URL}
import com.nicta.scoobi.io.FileSystems
import java.io.File
import com.nicta.scoobi.ScoobiConfiguration
import org.apache.hadoop.filecache.DistributedCache
import com.nicta.scoobi.ScoobiConfiguration._
import org.apache.hadoop.fs.Path

/**
 * This trait defines:
 *
 *  - the library jars which can be uploaded to the cluster
 *  - a method to upload and reference them on the classpath for cluster jobs
 */
trait LibJars {

  /**
   * @return the name of the directory to use when loading jars to the filesystem.
   *         the path which will be used will be relative to the user home on the cluster
   */
  def libjarsDirectory = "libjars/"

  /**
   * @return the list of library jars to upload, provided by the jars loaded by the current classloader
   */
  def jars: Seq[URL] = Thread.currentThread.getContextClassLoader.asInstanceOf[URLClassLoader].getURLs.filter { url =>
    Seq(".ivy2", ".m2").exists(url.getFile.contains) || url.getFile.contains("scala-library")
  }

  /**
   * @return the remote jars
   */
  def uploadedJars(implicit configuration: ScoobiConfiguration): Seq[Path] = FileSystems.listFiles(libjarsDirectory)

  /**
   * upload the jars which don't exist yet in the library directory on the cluster
   */
  def uploadLibJars(implicit configuration: ScoobiConfiguration) = {
    FileSystems.uploadNewJars(jars.map(url => new File(url.getFile)), libjarsDirectory)
    configureJars
  }

  /**
   * @return a configuration where the appropriate properties are set-up for uploaded jars: distributed files + classpath
   */
  def configureJars(implicit configuration: ScoobiConfiguration) = {
    uploadedJars.foreach(path => DistributedCache.addFileToClassPath(path, configuration))
    configuration.addValues("mapred.classpath", jars.map(j => libjarsDirectory+(new File(j.getFile).getName)), ":")
  }
}

