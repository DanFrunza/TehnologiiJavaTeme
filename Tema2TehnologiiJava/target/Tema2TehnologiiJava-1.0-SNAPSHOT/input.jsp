<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload File</title>
</head>
<body>
    <h1>Upload a Text File</h1>
    <form action="FileUploadServlet" method="post" enctype="multipart/form-data">
        <label for="file">Choose a text file:</label>
        <input type="file" id="file" name="file" accept=".txt" required>
        <br><br>
        
        <!-- CAPTCHA: Întrebare simpl? -->
        <label for="captchaAnswer">What is 3 + 2?</label>
        <input type="text" id="captchaAnswer" name="captchaAnswer" required>
        <br><br>
        
        <input type="submit" value="Upload">
    </form>
</body>
</html>
