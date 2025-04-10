# MinIO Provider
[![Java CI with Maven](https://github.com/droberts-ctrlo/minio-provider/actions/workflows/maven.yml/badge.svg)](https://github.com/droberts-ctrlo/minio-provider/actions/workflows/maven.yml)

Provider to allow upload and download of files to and from MinIO service

## Environment Variables for Running Service

`MINIO_ENDPOINT` - The URL of the MinIO service<br />
`MINIO_BUCKET` - The bucket to link the service to<br />
`MINIO_KEY` - Key for access to the MinIO service<br />
`MINIO_SECRET` - Secret for access to the MinIO service

## Available Endpoints

POST `/api/v1/upload` - Upload a file (as `multipart/form-data`) to the service<br />
GET `/api/v1/download/{filename}` - Download a file `{filename}` from the service<br />
DELETE `/api/v1/delete/{filename}` - Delete a file `{filename}` from the service
