<template>
  <label :for="newID"> {{ label }}</label>
  <br />
  <input
    :type="modelType"
    :value="modelValue"
    v-bind="{
      ...$attrs,
      onInput: updateValue
    }"
  />
</template>

<script>
import NewID from '@/util/NewID'
import SetupFormComponent from '@/util/SetupFormComponent'

export default {
  name: 'BaseInput',
  props: {
    label: {
      type: String,
      default: ''
    },
    modelType: {
      type: String,
      required: true
    },
    modelValue: {
      type: [String, Number],
      default: ''
    }
  },
  setup(props, context) {
    const { updateValue } = SetupFormComponent(props, context)
    const newID = NewID().getID()
    return { newID, updateValue }
  }
}
</script>

<style scoped>
label,
input {
  display: block;
}

label {
  font-size: 10px;
  float: left;
}

input {
  background-color: white;
  background-position: 10px 10px;
  background-repeat: no-repeat;
  padding-left: 6px;
  margin-bottom: 15px;
  margin-top: 5px;
  height: 20px;
  font-size: 18px;
}

input[type='submit'] {
  display: inline;
  height: 30px;
  float: none;
  background-color: #e476fa;
}
</style>
