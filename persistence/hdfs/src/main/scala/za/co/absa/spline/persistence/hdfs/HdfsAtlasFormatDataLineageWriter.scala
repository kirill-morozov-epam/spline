package za.co.absa.spline.persistence.hdfs

import java.util.UUID

import org.apache.atlas.hook.AtlasHook
import org.apache.atlas.notification.AbstractNotification
import org.apache.atlas.v1.model.notification.HookNotificationV1
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.permission.FsPermission
import org.apache.hadoop.fs.{FileSystem, Path}
import org.slf4s.Logging
import za.co.absa.spline.common.ARM._
import za.co.absa.spline.model.DataLineage
import za.co.absa.spline.persistence.api.DataLineageWriter
import za.co.absa.spline.persistence.atlas.conversion.DataLineageToTypeSystemConverter

import scala.collection.JavaConversions._
import scala.concurrent.{ExecutionContext, Future, blocking}

class HdfsAtlasFormatDataLineageWriter(hadoopConfiguration: Configuration, fileName: String, filePermissions: FsPermission) extends AtlasHook with DataLineageWriter with Logging {


  private val user = "spline"

  override def getNumberOfRetriesPropertyKey: String = "atlas.hook.spline.numRetries"

  override def store(lineage: DataLineage)(implicit ec: ExecutionContext): Future[Unit] = Future {
    val entityCollections = DataLineageToTypeSystemConverter.convert(lineage)
    val jsons = new java.util.ArrayList[String]
    val notification = new HookNotificationV1.EntityCreateRequest(user, entityCollections)
    AbstractNotification.createNotificationMessages(notification, jsons)
    jsons.foreach(x => {
      val path = new Path(fileName, UUID.randomUUID().toString)
      persistToHdfs(x, path)
    })

  }

  private def persistToHdfs(content: String, path: Path): Unit = blocking {
    val fs = FileSystem.get(hadoopConfiguration)
    log debug s"Writing lineage to $path"
    using(fs.create(
      path,
      filePermissions,
      true,
      hadoopConfiguration.getInt("io.file.buffer.size", 4096),
      fs.getDefaultReplication(path),
      fs.getDefaultBlockSize(path),
      null)) {
      _.write(content.getBytes)
    }
  }

}
