<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
<head>
    <title>Upload File Request Page</title>
</head>
<body>

<form method="POST" action="uploadFile" enctype="multipart/form-data">
    File to upload: <input type="file" name="file"><br />
    Name: <input type="text" name="name"><br /> <br />
    <input type="submit" value="Upload"> Press here to upload the file!
</form>

</body>
</html>