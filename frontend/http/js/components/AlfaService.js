import axios from 'axios';

export default function AlfaService(apiUrl) {
  const getItems = async () => {
    const { data } = await axios.get(apiUrl);
    return data;
  };

  const getItem = async (id) => {
    const { data } = await axios.get(`${apiUrl}/${id}`);
    return data;
  };

  const createItem = async (item) => {
    await axios.post(apiUrl, item);
  };

  const updateItem = async (id, item) => {
    await axios.put(`${apiUrl}/${id}`, item);
  };

  const deleteItem = async (id) => {
    await axios.delete(`${apiUrl}/${id}`);
  };

  return {
    getItems,
    getItem,
    createItem,
    updateItem,
    deleteItem,
  };
}
