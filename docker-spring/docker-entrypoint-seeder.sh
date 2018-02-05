#!/bin/sh

su - jeff -c '
  vault write secret/config-server privateKey=@/home/jeff/.ssh/id_rsa uri=${GIT_URI}
  git config --global user.email "jeff@edwardsonthe.net"
  git config --global user.name "Jeff Edwards"
  cd /home/jeff
  echo "~~~ GIT_URI: ${GIT_URI}"
  git clone ${GIT_URI}
  cd repo
  cp /tmp/repo/* .
  git add -A .
  git commit -m "seeded"
  git push
'

/usr/sbin/sshd -D
