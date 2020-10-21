# spring-batch-emailer - NEW VERSION
Spring Batch Emailer

1. Enable [Enable the Gmail API.](https://developers.google.com/gmail/api/quickstart/java)

2. Download Client Configuration as `credentials.json` and place it in the directory `src/main/resources`.

3. Check the username in `application.properties` file.

		spring.mail.username

4. Check the input file path in `application.properties` file and ensure the presence of input file in the specified path.

		input.filepath

5. On first run, it will ask us to visit a URL and authorize the application. On successful authorization, it will create a file, `StoredCredential` in the directory, `tokens`. Kindly note that, if you delete the file, it will again ask for authorization.


# spring-batch-emailer - OLD VERSION
Spring Batch Emailer

1. Check the login credentials in `application.properties` file.

		spring.mail.username

		spring.mail.password


> You may have to enable **less secure option** in Gmail to be able to use its SMTP server. [See more...](https://support.google.com/accounts/answer/6010255?hl=en)

2. Check the input file path in `application.properties` file and ensure the presence of input file in the specified path.

		input.filepath