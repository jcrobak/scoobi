package com.nicta.scoobi.io

import java.io.File
import org.apache.hadoop.fs.{Path, FileSystem}
import org.apache.hadoop.filecache.DistributedCache
import com.nicta.scoobi.ScoobiConfiguration
import com.nicta.scoobi.ScoobiConfiguration._

/**
 *
 */
private [scoobi]
trait FileSystems {

  /**
   * Upload additional local jars to a destination directory on the hdfs
   * @return the original sequence of files
   */
  def uploadNewJars(sourceFiles: Seq[File], dest: String)(implicit configuration: ScoobiConfiguration): Seq[File] = {
    DistributedCache.createSymlink(configuration)
    uploadNewFiles(sourceFiles, dest) { path =>
      DistributedCache.addFileToClassPath(path, configuration)
      path
    }
  }

  /**
   * Upload additional local files to a destination directory on the hdfs
   */
  def uploadNewFiles(sourceFiles: Seq[File], dest: String)
                    (onRemoteFiles: Path => Path = identity)(implicit configuration: ScoobiConfiguration): Seq[File] = {

    val uploaded = listFiles(dest)

    val newFiles = sourceFiles.filterNot((f: File) => uploaded.map(_.getName).contains(f.getName))
    newFiles.map { file: File =>
      fileSystem.copyFromLocalFile(new Path(file.getPath), new Path(dest))
    }

    uploaded foreach onRemoteFiles
    sourceFiles
  }

  /**
   * @return the list of files in a given directory on the file system
   */
  def listFiles(dest: String)(implicit configuration: ScoobiConfiguration): Seq[Path] = {
    if (!fileSystem.exists(new Path(dest))) fileSystem.mkdirs(new Path(dest))
    fileSystem.listStatus(new Path(dest)).map(_.getPath)
  }

  /**
   * @return the file system for a given configuration
   */
  def fileSystem(implicit configuration: ScoobiConfiguration) = FileSystem.get(configuration)

}

private [scoobi]
object FileSystems extends FileSystems