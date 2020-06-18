# spring-batch-emailer
Spring Batch Emailer

1. Check the login credentials in `application.properties` file.

		spring.mail.username

		spring.mail.password


> You may have to enable **less secure option** in Gmail to be able to use its SMTP server. [See more...](https://support.google.com/accounts/answer/6010255?hl=en)

2. Check the input file path in `application.properties` file and ensure the presence of input file in the specified path.

		input.filepath