import dayjs from 'dayjs';

export const toCapitalize = (str) => {
  if (!str) return "";
  const tmp = str.toLowerCase();
  return tmp.charAt(0).toUpperCase() + tmp.slice(1);
};

export const convertToDayBackend = (day) => {
  const formattedDate = dayjs(day).format('DD/MM/YYYY');
  return formattedDate;
};
