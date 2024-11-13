import axios from 'axios';

export default function BetaService(apiUrl) {
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

  return {
    getItems,
    getItem,
    createItem,
    updateItem,
  };
}
