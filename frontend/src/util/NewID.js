let newID = 1
export default function NewID() {
  const getID = () => {
    newID++
    return newID
  }
  return { getID }
}
