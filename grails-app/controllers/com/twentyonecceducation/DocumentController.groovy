package com.twentyonecceducation


import com.twentyonecceducation.security.SecRole
import com.twentyonecceducation.security.SecUser
import grails.plugin.springsecurity.annotation.Secured
import org.hsqldb.User
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
@Secured([SecRole.ROLE_USER])
class DocumentController {

//    static allowedMethods = [save: "POST"]
    def springSecurityService
//    String searchText
    def index(String searchText) {
//        redirect(action: "list", params: params)
//        println(params)
        if (searchText==null){
            [document: Document.list(params)]
        }else {
            def docs = Document.where {name =~"%${searchText}%"}.list()
            println("There found ${docs.size()}")
            [documents: docs,document:Document.list()]
        }

    }
    def list() {
        params.max = 10
        [documentInstanceList: Document.list(params), documentInstanceTotal: Document.count()]
    }
    def create() {
    }
    def upload() {
        def file = request.getFile('file')
        if(file.empty) {
            flash.message = "File cannot be empty"
        } else {
            def documentInstance = new Document()
            documentInstance.name = file.originalFilename
            documentInstance.data = file.getBytes()
            documentInstance.save()
        }
        redirect (action:'list')
    }

    def download(long id) {
        Document documentInstance = Document.get(id)
        if ( documentInstance == null) {
            flash.message = "Document not found."
            redirect (action:'list')
        } else {
            response.setContentType("APPLICATION/OCTET-STREAM")
            response.setHeader("Content-Disposition", "Attachment;Filename=\"${documentInstance.name}\"")
            def outputStream = response.getOutputStream()
            outputStream << documentInstance.data
            outputStream.flush()
            outputStream.close()
        }
    }
//    def downloadSampleZip() {
////        Tell the browser that your output is binary and the filename for download:
//        response.setContentType('APPLICATION/OCTET-STREAM')
//        response.setHeader('Content-Disposition', 'Attachment;Filename="example.zip"')
////        Creating a ZIP file for download is easy. Just instantiate ZipOutputStream passing response.outputStream:
//        ZipOutputStream zip = new ZipOutputStream(response.outputStream);
////        Creating a file inside the zip is accomplished by instantiating a ZipEntry and put it inside the ZIP file. You just provide the contents of the file by providing it's binary:
//        def file1Entry = new ZipEntry('first_file.txt');
//        zip.putNextEntry(file1Entry);
//        zip.write("This is the content of the first file".bytes);
//        def file2Entry = new ZipEntry('second_file.txt');
//        zip.putNextEntry(file2Entry);
//        zip.write("This is the content of the second file".bytes);
//        zip.close();
//    }

//

}
