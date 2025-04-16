from flask import Flask, request, jsonify
import pickle
import numpy as np

# Load the trained model.
# Make sure that model1.pkl is in the specified path.
model = pickle.load(open('C:\\Users\\manas\\Heart-Disease-Prediction-App\\API\\model1.pkl', 'rb'))

app = Flask(__name__)

@app.route('/')
def home():
    return "Heart Disease Prediction App"

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Retrieve and convert input values to the proper numeric types.
        cp = int(request.form.get('cp', 0))
        thalach = int(request.form.get('thalach', 0))
        slope = int(request.form.get('slope', 0))
        restecg = int(request.form.get('restecg', 0))
        chol = float(request.form.get('chol', 0))
        trestbps = float(request.form.get('trestbps', 0))
        fbs = int(request.form.get('fbs', 0))
        oldpeak = float(request.form.get('oldpeak', 0))

        # Build the input array.
        input_query = np.array([[cp, thalach, slope, restecg, chol, trestbps, fbs, oldpeak]])

        # Perform prediction.
        result = model.predict(input_query)[0]

        # Return the prediction as JSON.
        return jsonify({'heart_disease_prediction': str(result)})
    except Exception as e:
        # In case of any error, return it as a JSON response.
        return jsonify({'error': str(e)}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)