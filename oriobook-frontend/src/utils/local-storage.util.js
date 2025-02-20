export const setToLocalStorage = (key, data) => {
  if (
    typeof window !== "undefined" &&
    typeof window.localStorage !== "undefined"
  ) {
    try {
      localStorage.setItem(key, JSON.stringify(data));
    } catch (error) {
      console.error("Error saving to local storage:", error);
    }
  } else {
    console.warn("localStorage is not available");
  }
};

export const getFromLocalStorage = (key) => {
  if (
    typeof window !== "undefined" &&
    typeof window.localStorage !== "undefined"
  ) {
    try {
      const dataString = localStorage.getItem(key);
      if (dataString) {
        return JSON.parse(dataString);
      } else {
        return null;
      }
    } catch (error) {
      console.error("Error getting from local storage:", error);
      return null;
    }
  } else {
    console.warn("localStorage is not available");
    return null;
  }
};

export const removeFromLocalStorage = (key) => {
  if (
    typeof window !== "undefined" &&
    typeof window.localStorage !== "undefined"
  ) {
    try {
      localStorage.removeItem(key);
    } catch (error) {
      console.error("Error removing from local storage:", error);
    }
  } else {
    console.warn("localStorage is not available");
  }
};
