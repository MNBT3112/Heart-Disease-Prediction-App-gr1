from flask import Flask, request, jsonify
import pickle
import numpy as np
import os

# Load the trained model using a relative path or environment variable
model_path = os.getenv('MODEL_PATH', 'model1.pkl')  # Default to 'model1.pkl'
model = pickle.load(open(model_path, 'rb'))

app = Flask(__name__)

@app.route('/')
def home():
    return "Heart Disease Prediction App"

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Retrieve and convert input values to proper numeric types
        cp = int(request.form.get('cp', None))
        thalach = int(request.form.get('thalach', None))
        slope = int(request.form.get('slope', None))
        restecg = int(request.form.get('restecg', None))
        chol = float(request.form.get('chol', None))
        trestbps = float(request.form.get('trestbps', None))
        fbs = int(request.form.get('fbs', None))
        oldpeak = float(request.form.get('oldpeak', None))

        # Check for missing values
        if None in [cp, thalach, slope, restecg, chol, trestbps, fbs, oldpeak]:
            return jsonify({'error': 'All fields are required'}), 400

        # Build the input array
        input_query = np.array([[cp, thalach, slope, restecg, chol, trestbps, fbs, oldpeak]])

        # Perform prediction
        result = model.predict(input_query)[0]

        # Return the prediction as JSON
        return jsonify({'heart_disease_prediction': str(result)})

    except ValueError:
        return jsonify({'error': 'Invalid input type, check your data format'}), 400
    except Exception as e:
        return jsonify({'error': 'An unexpected error occurred: ' + str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)