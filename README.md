[![Actions Status](https://github.com/SMATechnologies/opcon-commons-java/workflows/Deploy%20Release/badge.svg)](https://github.com/SMATechnologies/opcon-commons-java/actions)

[![Actions Status](https://github.com/SMATechnologies/opcon-commons-java/workflows/Deploy%20Snapshot/badge.svg)](https://github.com/SMATechnologies/opcon-commons-java/actions)

# OpCon Commons library
Java commons library for OpCon

# Disclaimer
No Support and No Warranty are provided by SMA Technologies for this project and related material. The use of this project's files is on your own risk.

SMA Technologies assumes no liability for damage caused by the usage of any of the files offered here via this Github repository.

# Prerequisites

Java 11

# Instructions

Available from Central Maven Repository (https://search.maven.org/artifact/com.smatechnologies/opcon-commons):
```
<dependency>
    <groupId>com.smatechnologies</groupId>
    <artifactId>opcon-commons</artifactId>
    <version>1.0.1</version>
</dependency>
```

# Manually Deploy (authorised person only)

## Prerequisites

- Maven
- GNUPG (https://gnupg.org) or GPG4Win (https://gpg4win.org)

## Prepare

- Import Keys:
  ```
  gpg --import smatechnologies.com_pub.gpg
  gpg --allow-secret-key-import --import smatechnologies.com_sec.gpg
  ```

## Deploy


- Get KeyName from commands:
```
gpg --list-keys
gpg --list-secret-keys
```
- Deploy
```
mvn -Dgpg.keyname=FROM_GPG_LIST_KEYS_COMMAND -Dgpg.passphrase=PASSPHRASE clean deploy
```

# License
Copyright 2019 SMA Technologies

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at [apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

# Contributing
We love contributions, please read our [Contribution Guide](CONTRIBUTING.md) to get started!

# Code of Conduct
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](code-of-conduct.md)
SMA Technologies has adopted the [Contributor Covenant](CODE_OF_CONDUCT.md) as its Code of Conduct, and we expect project participants to adhere to it. Please read the [full text](CODE_OF_CONDUCT.md) so that you can understand what actions will and will not be tolerated.
