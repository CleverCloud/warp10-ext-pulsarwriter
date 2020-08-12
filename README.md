# warp10-ext-pulsarwriter

WarpScript function to send data to a Pulsar topic.

## Example

```warpscript
'your data'
{
 'biscuit' 'dGhpcyBpcyBub3QgYW4gYWN0dWFsIGJpc2N1aXQgdG9rZW4K'
 'endpoint' 'pulsar+ssl://hostname.tld:1234'
 'topic' 'tenant/namespace/mytopic'
}
SENDTOPULSAR
```
