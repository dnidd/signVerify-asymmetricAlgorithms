## Sign and verify with asymmetric algorithms and key pairs

The primary objective of the application is to:<br>
<b>Generate a signature</b> to sign an arbitrary data file/document and
verify the signature's authenticity.

Signing/verifying algorithm used:
- SHA256 for message-digest
- DSA (with 1024-bit keys) for signature.

Purpose
- Integrity - check that data has not been tampered with.
- Authenticity - Check that signature/data is sent from correct/expected user with the private key.

The project contains the following three applications to run in CLI/terminal:<br>
- ```java KeyHandler <privateKey> <publicKey>``` creates a private/public key pair and saves the keys (not encrypted) to the two files ```<privateKey>``` and ```<publicKey>```.<br>
- ```java SignHandler <data> <privateKey> <signature>``` signing the file ```<data>``` with (the private) key ```<privateKey>``` and saves the signature in ```<signature>```.<br>
- ```java VerifyHandler <data> <publicKey> <signature>``` checks if (the public) key in the file ```<publicKey>``` and the signature in the file ```<signature>``` verifies the file ```<data>```.<br>

One sending user generates a key pair (private & public)
and the two separate keys are written to files.
A data file is signed with a Signature created using the private key.
The signature is written to file Signature.

One receiving user receives document, signature and the public key.
The public key is used together with the signature to verify the
authenticity of the file.


### Compile all .java files:
Inside where .java-files exist (/src):
javac *.java


### <u>Execute from src:</u>
<span style="color:lightgreen">Files created/existing in src.</span><br>


🟢 ```java KeyHandler <privateKey> <publicKey>```<br>
🟢 ```java SignHandler <data> <privateKey> <signature>```<br>
🟢 ```java VerifyHandler <data> <publicKey> <signature>```


### Info:
<b>privateKey, publicKey:</b> name of key files in which keys will be stored.<br>
<b>data:</b> existing file containing text to be signed.<br>
<b>signature:</b> name of file holding private key signed data from file 'data'.<br>


### Generate keys:
- https://docs.oracle.com/javase/tutorial/security/apisign/step2.html
- https://docs.oracle.com/en/java/javase/24/security/java-cryptography-architecture-jca-reference-guide.html#GUID-ED6EDA78-8D20-4059-92E1-FBDDE4D3DFE6
- https://docs.oracle.com/en/java/javase/23/security/java-cryptography-architecture-jca-reference-guide.html#GUID-D4A4B706-6C4F-436D-B5ED-A29C98260578

### Recreate private/public key from file:
- https://stackoverflow.com/questions/26648469/how-to-convert-a-byte-array-to-dsa-private-key
- https://ssojet.com/keypair-generation/generate-keypair-using-dsa-in-java#loading-and-using-the-keypair
- https://docs.oracle.com/javase/tutorial/security/apisign/vstep2.html


### Sign the data:
- https://docs.oracle.com/javase/tutorial/security/apisign/step3.html
- https://docs.oracle.com/en/java/javase/23/security/java-cryptography-architecture-jca-reference-guide.html#GUID-7389E94B-A595-4020-A4D2-73FB0DD5A05A <br>


### Verify the signature:
- https://www.comp.nus.edu.sg/~cs3283/ftp/Java/JavaTutorial/security1.1/api/step4.html
- https://docs.oracle.com/javase/tutorial/security/apisign/vstep4.html
- https://docs.oracle.com/en/java/javase/23/security/java-cryptography-architecture-jca-reference-guide.html#GUID-7389E94B-A595-4020-A4D2-73FB0DD5A05A


1. Signing:
  - Create a message-digest on data file.
  - Use the private key to sign the message-digest.
  - Send the following:
    - The Data file
    - The public key (if the receiver don't have it already)
    - The Signature

2. Verifying:
   - The receiver gets:
     - The Data file
     - The public key (if the receiver don't have it already)
     - The Signature

   - Use the public key on the signature to fetch a message digest 1.
   - Create a message-digest 2 on the data file.
   - Verify if message digest 1 = message digest 2, if these equals, the message is verified.
