# Summary
The purpose of this repository is to define a consistent process to setup virtual development environment.  

# Dependencies
The following dependencies are assumed to be installed on the host machine used to build the virtual development
environment.  Missing dependencies will need to be installed before spinning up the virtual development environment.

## Vagrant
Vagrant is used to create and configure the virtual development environment.

### Vagrant Plugins
The following Vagrant plugins are required:

1. vagrant-hostmanager
1. vagrant-persistent-storage
1. vagrant-proxyconf
1. vagrant-reload
1. vagrant-vbguest

You can use the `vagrant plugin list` command to determine current plugins configured on your system.  Missing plugins
can be installed using the `vagrant plugin install <name>` command.

## Ansible
Vagrant uses Anisble as a provisioners.  Ansible can be installed on CentOS using the following command:

    $ sudo yum install ansible

## VirtualBox
The `Vagrantfile` associated with this repository uses VirtualBox as provider.

# Building the Virtual Development Environment
Execute the following command to configure the virtual development environment.

    $ vagrant up
