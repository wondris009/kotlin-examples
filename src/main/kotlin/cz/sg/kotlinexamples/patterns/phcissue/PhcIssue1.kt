package cz.sg.kotlinexamples.patterns.phcissue

interface RemoteInterface {
    fun myList(path: String): List<String>
    fun mySend(subDirectory: String): String?
    fun myGet(remotePath: String)
    fun myRemove(path: String)
}

//open class MyTemplateImplMain<F>(sessionFactory: SessionFactory<F>) : RemoteFileTemplate<F>(sessionFactory) {
//    fun mySend(message: Message<*>, subDirectory: String, mode: FileExistsMode): String? {
//        return super.send(message, subDirectory, mode)
//    }
//
//    fun myGet(remotePath: String, callback: InputStreamCallback) {
//        super.get(remotePath, callback)
//    }
//
//    fun myRemove(path: String) {
//        super.remove(path)
//    }
//}
//
//class RemoteInterfaceImpl1(sessionFactory: DefaultSftpSessionFactory): MyTemplateImplMain<SftpClient.DirEntry>(sessionFactory),RemoteInterface {
//    override fun myList(path: String): List<String> {
//        return super.list(path).filter { it.attributes.isRegularFile }.map { it.filename }
//    }
//
//}
//
//class RemoteInterfaceImpl2(sessionFactory: DefaultFtpsSessionFactory): MyTemplateImplMain<FTPFile>(sessionFactory),RemoteInterface {
//    override fun myList(path: String): List<String> {
//        return super.list(path).filter { it.isFile }.map { it.name }
//    }
//
//}