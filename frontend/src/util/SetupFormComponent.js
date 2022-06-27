export default function SetupFormComponent(props, { emit }) {
  const updateValue = (element) => {
    let val = element.target.value
    emit('update:modelValue', val)
  }

  return { updateValue }
}
