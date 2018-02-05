#!/bin/sh

IP_ADDR = `ip addr show eth0 | fgrep "inet " | sed -r -e 's/.*inet (.*)\/.*/\1/'`
VAULT_DEV_LISTEN_ADDRESS="${IP_ADDR}:${VAULT_PORT}"

/bin/vault server -dev \
   -dev-listen-address="${IP_ADDR}:${VAULT_PORT}" \
   -dev-root-token-id="${VAULT_TOKEN}"
