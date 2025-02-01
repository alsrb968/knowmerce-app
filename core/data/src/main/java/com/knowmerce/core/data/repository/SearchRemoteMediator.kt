package com.knowmerce.core.data.repository

//@OptIn(ExperimentalPagingApi::class)
//class SearchRemoteMediator(
//    private val local: DocumentDataSource,
//    private val remote: KakaoDataSource,
//) : RemoteMediator<Int, SearchContent>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, SearchContent>
//    ): MediatorResult {
//        val page = when (loadType) {
//            LoadType.REFRESH -> 1
//            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//            LoadType.APPEND -> {
//                val lastItem = state.lastItemOrNull()
//                if (lastItem == null) {
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                lastItem.page + 1
//            }
//        }
//    }
//
//}