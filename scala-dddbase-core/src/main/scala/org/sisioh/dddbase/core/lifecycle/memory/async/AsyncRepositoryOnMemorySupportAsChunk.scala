package org.sisioh.dddbase.core.lifecycle.memory.async

import org.sisioh.dddbase.core.lifecycle.{EntityIOContext, EntitiesChunk}
import org.sisioh.dddbase.core.lifecycle.async._
import org.sisioh.dddbase.core.lifecycle.memory.sync.SyncRepositoryOnMemory
import org.sisioh.dddbase.core.lifecycle.sync.SyncEntityReadableAsChunk
import org.sisioh.dddbase.core.model._
import scala.concurrent._

/**
 * [[org.sisioh.dddbase.core.lifecycle.memory.async.AsyncRepositoryOnMemorySupport]]に
 * [[org.sisioh.dddbase.core.lifecycle.EntitiesChunk]]のための機能を追加するトレイト。
 *
 * @tparam ID 識別子の型
 * @tparam E エンティティの型
 */
trait AsyncRepositoryOnMemorySupportAsChunk
[ID <: Identifier[_], E <: Entity[ID] with EntityCloneable[ID, E]]
  extends AsyncRepositoryOnMemorySupport[ID, E]
  with AsyncEntityReadableAsChunk[ID, E] {

  type Delegate <: SyncRepositoryOnMemory[ID, E] with SyncEntityReadableAsChunk[ID, E]

  def resolveAsChunk(index: Int, maxEntities: Int)
                  (implicit ctx: EntityIOContext[Future]): Future[EntitiesChunk[ID, E]] = {
    val asyncCtx = getAsyncWrappedEntityIOContext(ctx)
    implicit val executor = asyncCtx.executor
    future {
      implicit val syncCtx = asyncCtx.syncEntityIOContext
      delegate.resolveAsChunk(index, maxEntities).get
    }
  }

}