# Microservice Development

This example project focuses on how to dockerize Spring Boot Applications that run in an isolated environment, a.k.a. Docker container.  It highlights how to create a composition of containers, which depend on each other and are linked against each other in a virtual private network. Furthermore, it illustrates how the containers can be managed together with single commands.

Frameworks Leveraged from Spring IO Ecosystem
+ Spring Framework
  - Basis for all Spring IO Frameworks
  - Handles plumbing - configuration and context – of application beans
+ Spring Boot
  - Create a stand-alone Spring based application 
+ Spring Cloud Config
  - Externalized application configuration
+ Spring Cloud Netflix
  - Service registration and discovery from Netflix
  
_TODO: Discuss usage of docker, docker-compose, and Hashicorp Vault_

## Building Spring Boot Application Microservices

Execute the following from the command line:

```
$ cd spring
$ mvn clean package
```

## Building the Docker Images

Use `docker-compose` to build our `Docker` images.  Execute the following from the command line:

```
$ docker-compose build
```

Use the `docker images` command to show all top-level images in the repository.  Execute the following from the command line:

```
$ docker images
REPOSITORY                 TAG                 IMAGE ID            CREATED              SIZE
sandbox_example-consumer   latest              86192622e2bc        7 seconds ago        141MB
sandbox_example-producer   latest              04ca391170ea        30 seconds ago       141MB
sandbox_eureka-server      latest              50b5e2ee51a0        54 seconds ago       144MB
sandbox_config-server      latest              11f385b8fe87        About a minute ago   126MB
sandbox_seeder             latest              7538384a44dd        2 minutes ago        133MB
sandbox_git-server         latest              06e6a81d61b0        7 minutes ago        29.4MB
sandbox_vault-server       latest              695818d94d01        8 minutes ago        108MB
openjdk                    alpine              a8bd10541772        4 weeks ago          101MB
alpine                     3.6                 76da55c8019d        4 weeks ago          3.96MB
```

## Running the Docker Images

Use `docker-compose` to spin-up our `Docker` images.  Execute the following from the command line:

```
$ docker-compose up
```

Typically, `Docker` containers are ran in the background using the detach mode option (`-d`).  However, in this example we want to see the standard out from the running containers.

## Interact with the Running Docker Containers

In a new command prompt, use the `docker ps` command to inspect the running containers.

```
$ docker ps
CONTAINER ID        IMAGE                      COMMAND                  CREATED             STATUS              PORTS                              NAMES
edbdc212c82a        sandbox_example-consumer   "java -Djava.secur..."   5 minutes ago       Up 5 minutes        8888/tcp, 0.0.0.0:8890->8890/tcp   sandbox_example-consumer_1
d62b14966418        sandbox_example-producer   "java -Djava.secur..."   5 minutes ago       Up 5 minutes        8888/tcp, 0.0.0.0:8889->8889/tcp   sandbox_example-producer_1
e803d8a8a4ac        sandbox_eureka-server      "java -Djava.secur..."   5 minutes ago       Up 5 minutes        0.0.0.0:8761->8761/tcp, 8888/tcp   sandbox_eureka-server_1
70dfc088f6aa        sandbox_config-server      "java -Djava.secur..."   5 minutes ago       Up 5 minutes        0.0.0.0:8888->8888/tcp             sandbox_config-server_1
b527fc40cc4c        sandbox_seeder             "docker-entrypoint.sh"   5 minutes ago       Up 5 minutes        0.0.0.0:9022->22/tcp               sandbox_seeder_1
7c6f4f55dbce        sandbox_git-server         "docker-entrypoint.sh"   5 minutes ago       Up 5 minutes        0.0.0.0:8022->22/tcp               sandbox_git-server_1
f35b1e9ee0e6        sandbox_vault-server       "docker-entrypoint.sh"   5 minutes ago       Up 5 minutes        0.0.0.0:8200->8200/tcp             sandbox_vault-server_1
```

Determine the IP of the `example-consumer`.  In this example, the container id of the `example-consumer` is `edbdc212c82a`.  We can determine the IP address by inspecting the running container with this value as follows:

```
$ docker inspect edbdc212c82a
[
    {
        "Id": "edbdc212c82ac0286a53de694375a5a7ed73ab2f867be9190105eb19b96515b8",
        "Created": "2017-10-17T15:33:29.490305298Z",
        "Path": "java",
        "Args": [
            "-Djava.security.egd=file:/dev/./urandom",
            "-jar",
            "/home/jeff/app.jar"
        ],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 14494,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2017-10-17T15:33:31.515503092Z",
            "FinishedAt": "0001-01-01T00:00:00Z"
        },
        "Image": "sha256:86192622e2bc480a89e30d9c756cd0ac7e45a14d46b826fda39eaa2b460f879f",
        "ResolvConfPath": "/var/lib/docker/containers/edbdc212c82ac0286a53de694375a5a7ed73ab2f867be9190105eb19b96515b8/resolv.conf",
        "HostnamePath": "/var/lib/docker/containers/edbdc212c82ac0286a53de694375a5a7ed73ab2f867be9190105eb19b96515b8/hostname",
        "HostsPath": "/var/lib/docker/containers/edbdc212c82ac0286a53de694375a5a7ed73ab2f867be9190105eb19b96515b8/hosts",
        "LogPath": "/var/lib/docker/containers/edbdc212c82ac0286a53de694375a5a7ed73ab2f867be9190105eb19b96515b8/edbdc212c82ac0286a53de694375a5a7ed73ab2f867be9190105eb19b96515b8-json.log",
        "Name": "/sandbox_example-consumer_1",
        "RestartCount": 0,
        "Driver": "overlay",
        "Platform": "linux",
        "MountLabel": "",
        "ProcessLabel": "",
        "AppArmorProfile": "",
        "ExecIDs": null,
        "HostConfig": {
            "Binds": [],
            "ContainerIDFile": "",
            "LogConfig": {
                "Type": "json-file",
                "Config": {}
            },
            "NetworkMode": "sandbox_br0",
            "PortBindings": {
                "8890/tcp": [
                    {
                        "HostIp": "",
                        "HostPort": "8890"
                    }
                ]
            },
            "RestartPolicy": {
                "Name": "",
                "MaximumRetryCount": 0
            },
            "AutoRemove": false,
            "VolumeDriver": "",
            "VolumesFrom": [],
            "CapAdd": null,
            "CapDrop": null,
            "Dns": null,
            "DnsOptions": null,
            "DnsSearch": null,
            "ExtraHosts": null,
            "GroupAdd": null,
            "IpcMode": "shareable",
            "Cgroup": "",
            "Links": null,
            "OomScoreAdj": 0,
            "PidMode": "",
            "Privileged": false,
            "PublishAllPorts": false,
            "ReadonlyRootfs": false,
            "SecurityOpt": null,
            "UTSMode": "",
            "UsernsMode": "",
            "ShmSize": 67108864,
            "Runtime": "runc",
            "ConsoleSize": [
                0,
                0
            ],
            "Isolation": "",
            "CpuShares": 0,
            "Memory": 0,
            "NanoCpus": 0,
            "CgroupParent": "",
            "BlkioWeight": 0,
            "BlkioWeightDevice": null,
            "BlkioDeviceReadBps": null,
            "BlkioDeviceWriteBps": null,
            "BlkioDeviceReadIOps": null,
            "BlkioDeviceWriteIOps": null,
            "CpuPeriod": 0,
            "CpuQuota": 0,
            "CpuRealtimePeriod": 0,
            "CpuRealtimeRuntime": 0,
            "CpusetCpus": "",
            "CpusetMems": "",
            "Devices": null,
            "DeviceCgroupRules": null,
            "DiskQuota": 0,
            "KernelMemory": 0,
            "MemoryReservation": 0,
            "MemorySwap": 0,
            "MemorySwappiness": null,
            "OomKillDisable": false,
            "PidsLimit": 0,
            "Ulimits": null,
            "CpuCount": 0,
            "CpuPercent": 0,
            "IOMaximumIOps": 0,
            "IOMaximumBandwidth": 0
        },
        "GraphDriver": {
            "Data": {
                "LowerDir": "/var/lib/docker/overlay/355be58d224d3bade9c7f52cae47f14d28d452012eeb3e387eda61e1cad0b28a/root",
                "MergedDir": "/var/lib/docker/overlay/ef64477d3fc16c3506dfa6ce2c4a9ec50fbac042b5a768db4d35dfc0392b26f1/merged",
                "UpperDir": "/var/lib/docker/overlay/ef64477d3fc16c3506dfa6ce2c4a9ec50fbac042b5a768db4d35dfc0392b26f1/upper",
                "WorkDir": "/var/lib/docker/overlay/ef64477d3fc16c3506dfa6ce2c4a9ec50fbac042b5a768db4d35dfc0392b26f1/work"
            },
            "Name": "overlay"
        },
        "Mounts": [
            {
                "Type": "volume",
                "Name": "1cf234c207f3fdbae6b796cced333eb2b3ecb09132e7fd9b70aca35abe8c9074",
                "Source": "/var/lib/docker/volumes/1cf234c207f3fdbae6b796cced333eb2b3ecb09132e7fd9b70aca35abe8c9074/_data",
                "Destination": "/tmp",
                "Driver": "local",
                "Mode": "",
                "RW": true,
                "Propagation": ""
            }
        ],
        "Config": {
            "Hostname": "edbdc212c82a",
            "Domainname": "",
            "User": "jeff",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "ExposedPorts": {
                "8888/tcp": {},
                "8890/tcp": {}
            },
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "CONFIG_SERVER_URI=http://config.local:8888/",
                "SERVER_PORT=8890",
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin",
                "LANG=C.UTF-8",
                "JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk",
                "JAVA_VERSION=8u131",
                "JAVA_ALPINE_VERSION=8.131.11-r2"
            ],
            "Cmd": [
                "-Djava.security.egd=file:/dev/./urandom",
                "-jar",
                "/home/jeff/app.jar"
            ],
            "ArgsEscaped": true,
            "Image": "sandbox_example-consumer",
            "Volumes": {
                "/tmp": {}
            },
            "WorkingDir": "",
            "Entrypoint": [
                "java"
            ],
            "OnBuild": null,
            "Labels": {
                "com.docker.compose.config-hash": "606bf9b1b2624c3e4dfa5ee6b78385f12cc78d30784047633c47adcb6b1bbfaf",
                "com.docker.compose.container-number": "1",
                "com.docker.compose.oneoff": "False",
                "com.docker.compose.project": "sandbox",
                "com.docker.compose.service": "example-consumer",
                "com.docker.compose.version": "1.16.1"
            }
        },
        "NetworkSettings": {
            "Bridge": "",
            "SandboxID": "77af2e4374c39e7470e20b61ada827bde706d7cb562ae526389656eeb0653e70",
            "HairpinMode": false,
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "Ports": {
                "8888/tcp": null,
                "8890/tcp": [
                    {
                        "HostIp": "0.0.0.0",
                        "HostPort": "8890"
                    }
                ]
            },
            "SandboxKey": "/var/run/docker/netns/77af2e4374c3",
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "EndpointID": "",
            "Gateway": "",
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "",
            "IPPrefixLen": 0,
            "IPv6Gateway": "",
            "MacAddress": "",
            "Networks": {
                "sandbox_br0": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": [
                        "producer.local",
                        "edbdc212c82a",
                        "example-consumer"
                    ],
                    "NetworkID": "2b9b6985fb84f0aeda9720184b824ffc18793461fc7e908a1a6271ac7f61a14b",
                    "EndpointID": "d57bef81eb2223eebc7fbeeae29d76d55e78a8467769161884f6690997e3c16c",
                    "Gateway": "172.28.0.1",
                    "IPAddress": "172.28.0.8",
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "MacAddress": "02:42:ac:1c:00:08",
                    "DriverOpts": null
                }
            }
        }
    }
]
```

From the above output, we determine that the IP address is `172.28.0.8`.

The `example-consumer` exposes a RESTful endpoint `/message`.  Execute the following from the command line to hit this endpoint.

```
$ curl http://172.28.0.8:8890/message
Hello World!
```

_TODO: Provide better documentation on the content of this project._
