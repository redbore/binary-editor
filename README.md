### Binary editor helps to parse files and edit them by specification

# Specification
## Example
```xml
<segments>
    <segment name="segmentName1">
        <field name="fieldName1" type="string" length="4"/>
        <field name="fieldName2" type="int32"/>
    </segment>
     <segment name="segmentName2" count="segmentName1.fieldName2">
        <field name="fieldName1" type="int16"/>
    </segment>
</segments>
```

### `<segments>`
```
File parser: Segment list. 
Manager: Table list
```
`attributes:`
 
### `<segment>`
```
File parser: Field groups description.
Manager: Table description.
```
`attributes:`
- name - (**required**, **unique**)
```
File parser: Segment name. 
Manager: Table name.
```
- count - (**default 1**)
```
File parser: Retry count. May be a link. 
Manager: Rows count.
```
### `<field>`
```
File parser: Specific type description.
Manager: Column description.
```

`attributes:`
- name - (**required**, **unique**)
```
File parser: Field name. 
Manager: Column name.
```
- type - (**required**)
```
File parser: Field type. Needed to correctly determine the size of the number. 
    - string - string type, needs length 
    - int8 - 1 byte integer
    - int16 - 2 byte integer
    - int32 - 4 byte integer
    - int64 - 8 byte integer
    - float32 - 4 byte real number
    - float64 - 8 byte real number
```
- length
```
File parser: String type length. May be a link
```
