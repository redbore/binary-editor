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
Segment list
```
`attributes:`
 
### `<segment>`

```
Description of the groups of types, we need to separate the fields in the sense. Can have more than one instance
```
`attributes:`
- name - Segment name (**required**, **unique**)
- count - Number of repetitions of the next segment

### `<field>`
```
Description of a specific type in a binary file
```
`attributes:`
- name - Field name (**required**, **unique**)
- type - Field type. Needed to correctly determine the size of the number (**required**)
    - **string** - string type, needs length 
    - **int16** - 2 byte integer
    - **int32** - 4 byte integer
    - **int64** - 8 byte integer
    - **float32** - 4 byte real number
    - **float64** - 8 byte real number
- length - Length of string type
