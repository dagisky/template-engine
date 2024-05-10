## Template Engine
A template requires a String file path, a dataTable and its parameter name.
A composite pattern and Strategy pattern is used to render JSON or XML template

The datatable is a Trie (Prefix Tree) data structure. This complements the composite pattern used to render the template. In ideal scenario the template will be loaded once, but we can modify and inject the datatable any number of times.

### Include a sub-template
The sub-template render engine will render a given template and dataTable to a `JSON` or `XML` string. The syntax will look as follows.
`{{> path/toTeplate }}`  // include

This loads the address `json` from `personal/address.json`
```
{
	fname: "Dagi",
	lname: "{{lname}}",
	address:"{{>personal/address}}"
}
```

`personal/address`  template
```json
{
	address1: {{address1}}
	address2: {{address2}}
	zip: {{zip}}
}
```

With this template the dataTable
```
address.address1 => "Address One "
address.address2 => "2133"
address.zip => "75430"
```

We can have a default template path and a relative path can be discovered based on the default template path.


